import React, { Component } from 'react';
import JqxInput from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxinput';
import JqxNumberInput from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxnumberinput';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import { FaPlus, FaMinus } from "react-icons/fa";
import Common from '../service/common.js';
import './record.css';

function Item({ col, viewModel, theme, onChange }) {
    if(col.type == null || col.type === 'text'){
        return <div className='scCenterXY scRecordItem'>
                    <div className='scColLabel'>
                        <div className='scLabel'>{col.label}</div>
                    </div>
                    <JqxInput disabled={viewModel} width={'100%'} value={col.value} onChange={onChange}/>
                </div>
    } else if(col.type === 'number'){
        return <JqxNumberInput width={'100%'} disabled={viewModel} value={col.value} placeHolder={col.label}  onChange={onChange} inputMode={'simple'}/>
    } else {
        return <div></div>
    }
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
                    return <div key={itemIndex} className='scRecordCols scCenterXY'>
                      <Item col={item} viewModel={viewModel} theme={theme} onChange={(event)=> {onChange(event, index, itemIndex, item)}}/>
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
        Common.subscribe(Common.WATCH.THEME, this.themeChange);
    }

    themeChange(newValue){
        this.theme = newValue;
    }

    inputOnChange(event, index, itemIndex, item){
        if(item != null){
            item.value = event.currentTarget.value;
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

    render() {
        return(
            <div className='scRecordContainer'>                
                <RowItem rows={this.rows} viewModel={this.viewModel} theme={this.theme} buttonStyle={this.buttonStyle} onclick={(event) => {this.addRow(event)}} onChange={this.inputOnChange}/>          
            </div>
        )
    }
}

export default Record;