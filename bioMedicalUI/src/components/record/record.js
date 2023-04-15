import React, { Component } from 'react';
import JqxInput from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxinput';
import JqxNumberInput from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxnumberinput';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxDropDownList from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxdropdownlist';
import { FaPlus, FaMinus } from "react-icons/fa";
import Common from '../service/common.js';
import './record.css';

function Item({ col, row, viewModel, theme, onChange }) {
    let disabledField = (viewModel ? viewModel : col.disabled ? true : false);
    let html = <div></div>;
    let width = '100%';

    col.row = row;
    col.componentRef = React.createRef();

    if(col.type == null || col.type === 'text'){
        html = <div className='scCenterXY scRecordItem'>
                    <div className='scColLabel'>
                        <div className='scLabel'>{col.label}
                            <span className='scRed'>
                                <sup>{
                                    col.required ? ' *': ''
                                }</sup>
                            </span>
                        </div>
                    </div>
                    <JqxInput width={width} ref={col.componentRef} name={col.bind} disabled={disabledField} value={col.value} onChange={onChange}/>
                </div>
    } else if(col.type === 'number'){
        html = <JqxNumberInput width={width} ref={col.componentRef} disabled={disabledField} value={col.value} placeHolder={col.label}  onChange={onChange} inputMode={'simple'}/>
    } else if(col.type === 'option'){
        html = <div className='scCenterXY scRecordItem'>
            <div className='scColLabel'>
                <div className='scLabel'>{col.label}
                    <span className='scRed'>
                        <sup>{
                            col.required ? ' *': ''
                        }</sup>
                    </span>
                </div>
            </div>
            <JqxDropDownList width={width} ref={col.componentRef} disabled={disabledField} selectedIndex={col.selectedIndex} source={col.options} onChange={onChange}/>
        </div>
    }

    return html;    
}

function Icon({ isAdd }) {
    if(isAdd){
        return <FaMinus/>
    } else {
        return <FaPlus />
    }
}

function RowItem({rows, viewModel, theme, buttonStyle, onclick, onChange}){
    let html;
    
    html = rows.map((ele, index)=>{
        return <div key={index} className='scCenterXY scRecordRow'>{
            ele.map((item, itemIndex)=>{
                    let widthVal = (item.width != null ? item.width : '100%');        
                    let width = {width: widthVal};

                    if(widthVal.includes('px')){
                        width = {width: widthVal, minWidth: widthVal, maxWidth: widthVal};
                    }                    

                    return <div key={itemIndex} className='scRecordCols scCenterXY' style={width}>
                      <Item col={item} row={ele} viewModel={viewModel} theme={theme} onChange={(event)=> {onChange(event, index, itemIndex, item)}}/>
                    </div>
                })                                
            }
            <div className='scRecordButton scCenterXY'>
                <JqxButton theme={theme} disabled={viewModel} template={'primary'} width={40} style={buttonStyle} onclick={onclick}>
                    <span className='scHide'>{index}</span>
                    <Icon isAdd={ele.isAdd} />
                </JqxButton>
            </div>
        </div>
    });

    return html;
}

export class Record extends Component {
    rows;
    changeEvent = {};
    buttonStyle = { float: 'left', marginLeft: '4px', cursor: 'pointer' };
    viewModel = false;

    constructor(props){
        super(props);        

        this.rows = []; 
        let model = JSON.parse(JSON.stringify(this.props.model));
        this.rows.push(model); 
        this.themeChange = this.themeChange.bind(this);
        this.inputOnChange = this.inputOnChange.bind(this);
        this.addRow = this.addRow.bind(this);
        this.val = this.val.bind(this);
        this.setVal = this.setVal.bind(this);
        this.onViewModel = this.onViewModel.bind(this);
        this.setComboSource = this.setComboSource.bind(this);
        this.reset = this.reset.bind(this);
        Common.subscribe(Common.WATCH.THEME, this.themeChange);

        if(this.props.model != null){
            this.props.model.forEach((row)=>{
                this.changeEvent[row.bind] = row.change;
            })
        }
    }

    themeChange(newValue){
        this.theme = newValue;
    }

    inputOnChange(event, index, itemIndex, item){
        if(item != null){
            if(item.type == 'option'){
                item.value = event.args.item.originalItem;
                item.selectedIndex = event.args.item.index;
            }
            else {
                item.value = event.currentTarget.value;
            }
            
            if(item.dependentField != null){
                item.dependentField.forEach((field)=>{
                    item.row.forEach((col)=>{
                        if(col.bind == field){
                            col.required = (item.value != null && item.value.length > 0);
                        }
                    })
                });   
                
                this.forceUpdate();
            }

            if(this.changeEvent != null && this.changeEvent[item.bind] != null){
                this.changeEvent[item.bind](item.value, item.row);
            }
        }
    }

    addRow(event){
        if(!this.viewModel){
            let target = event.currentTarget;

            if(target != null && target.childNodes != null && target.childNodes.length > 0){
                let index = target.childNodes[0].innerHTML;
    
                if(!this.rows[index].isAdd || this.rows.length === 1){
                    let model = JSON.parse(JSON.stringify(this.props.model));
                    this.rows[index].isAdd = true;
                    this.rows.push(model);
                } else{
                    this.rows[index].isAdd = false;
                    this.rows.splice(index, 1);
                }
            }
    
            if(this.rows.length === 0){
                let model = JSON.parse(JSON.stringify(this.props.model));
                this.rows.push(model);
            }
            
            this.forceUpdate();
        }
    }

    val(){
        var result = [];

        if(this.rows != null && this.rows.length > 0){
            this.rows.forEach((elements) => {
                var data = null;

                elements.forEach((item) => {
                    if(item.value != null && item.value != ''){
                        if(data == null){
                            data = {};
                        }

                        data[item.bind] = item.value;
                    }
                })

                if(data != null){
                    data.id = elements.id;
                    result.push(Object.assign({}, data));
                }
            })
        }

        return result;
    }

    setVal(data){
        this.rows.length = 0;

        if(data != null){
            data.forEach((element) => {
                let model = JSON.parse(JSON.stringify(this.props.model));
                
                model.forEach((col) => {
                    col.value = (element[col.bind] != null ? element[col.bind] : '');
                });

                model.id = element.id;
                this.rows.push(model);
            });
        }

        if(this.rows.length ==0){
            let model = JSON.parse(JSON.stringify(this.props.model));
            this.rows.push(model);
        } else {
            this.rows.forEach((element, index) => {
                if(index < this.rows.length - 1){
                    element.isAdd = true;
                }
            });
        }

        this.forceUpdate();
    }

    onViewModel(viewModel){
        this.viewModel = viewModel;
        this.forceUpdate();
    }

    setComboSource(field, source){
        var mapOption = function(cols, col, opts){
            if(cols != null){
                cols.forEach((element, index) =>{
                    if(element.bind == col){
                        element.options = opts;
                    }
                });
            }
        }

        mapOption(this.props.model, field, source);

        this.rows.forEach((element, index) => {
            mapOption(element, field, source);
        });

        this.forceUpdate();
    };

    reset(){
        let model = JSON.parse(JSON.stringify(this.props.model));
        this.rows.length = 0;

        model.forEach((element, index) => {
            if(element.type == 'option'){
                element.selectedIndex = undefined;
            }
        });

        this.rows.push(model);
    }

    componentDidUpdate(){
        if(this.rows != null){
            this.rows.forEach((row)=>{
                row.forEach((col)=>{
                    if(col.type == 'option' && col.selectedIndex == undefined){
                        col.componentRef.current.clearSelection();
                    }
                })
            })
        }
    }

    render() {
        return(
            <div className='scRecordContainer'>                
                <RowItem rows={this.rows} viewModel={this.viewModel} theme={this.theme} buttonStyle={this.buttonStyle} onclick={(event) => {this.addRow(event)}} onChange={this.inputOnChange}/>          
            </div>
        )
    }
}

export default Record;