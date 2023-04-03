import React, { Component } from 'react';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxWindow from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxwindow';
import JqxTabs  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxtabs';
import JqxForm from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxform';
import JqxComboBox from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxcombobox';

import { FaDesktop } from "react-icons/fa";

import HttpAJAX from '../../service/httpAJAX.js';
import Common from '../../service/common.js';
import '../../page/page.css';
import './appointment.css';

export class Appointment extends Component {
    visibile = false;
    selectedTab = 0;
    formWidth = 800;
    labelWidth = 180;
    controlWidth = (this.formWidth / 2) - 54;
    
    constructor(props){
        super(props);

        this.window = React.createRef();
        this.tabRef = React.createRef();
        this.appointmentRef = React.createRef();
        this.paymentRef = React.createRef();

        let departmentOpts = [
            { value: '' },
            { value: 'Pediatrics' },
            { value: 'Neonatology' },
            { value: 'Obstetrics' },
            { value: 'Pediatric Surgery' },
        ];
     
        this.appointmentTemp = Common.getColTemplate({
            noOfCols: 2,
            labelWidth:this.labelWidth,
            controlWidth:this.controlWidth,
            rows:[
              {bind: 'department', label: 'Department', type: 'option',component: 'jqxDropDownList', options: departmentOpts},
              {bind: 'patientType', label: 'Patient Type', type:"option", component: 'jqxDropDownList', options: [ { value: '' }, { value: 'Consultation' }, { value: 'Diagnostics' } ]},
              {bind: 'referral', label: 'Referral', type:"option", component: 'jqxDropDownList', options: [ { value: '' }, { value: 'Yes' }, { value: 'No' } ]},
              {bind: 'visitType', label: 'Visit Type', type:"option", component: 'jqxDropDownList', options: [ { value: '' },{ value: 'First Visit' }, { value: 'Review' } ]},
              {bind: 'payeeType', label: 'Payee Type', type:"option", component: 'jqxDropDownList', options: [ { value: '' }, { value: 'Self' }, { value: 'Insurance' } ]}
            ]
        });

        this.paymentTemp = Common.getColTemplate({
            noOfCols: 2,
            labelWidth:this.labelWidth,
            controlWidth:this.controlWidth,
            rows:[
              {bind: 'consultationDoctor', label: 'Consultation-Doctor', type: 'option',component: 'jqxDropDownList', init: this.makeMultiSelectCombo, change: (value)=>{
                this.amountUpdate(value, 'consultAmount');
              }},
              {bind: 'consultAmount', label: 'Amount', disabled: true, change: (value)=>{
                this.totalAmountUpdate(['consultAmount', 'diagAmount', 'serviceAmount'], 'totalAmount');
              }},
              {bind: 'diagnostics', label: 'Diagnostics', type: 'option',component: 'jqxDropDownList', init: this.makeMultiSelectCombo, change: (value)=>{
                this.amountUpdate(value, 'diagAmount');
              }},
              {bind: 'diagAmount', label: 'Amount', disabled: true, change: (value)=>{
                this.totalAmountUpdate(['consultAmount', 'diagAmount', 'serviceAmount'], 'totalAmount');
              }},
              {bind: 'services', label: 'Services', type: 'option',component: 'jqxDropDownList', init: this.makeMultiSelectCombo, change: (value)=>{
                this.amountUpdate(value, 'serviceAmount');
              }},
              {bind: 'serviceAmount', label: 'Amount', disabled: true, change: (value)=>{
                this.totalAmountUpdate(['consultAmount', 'diagAmount', 'serviceAmount'], 'totalAmount');
              }},
              {bind: 'totalAmount', label: 'Total Amount', disabled: true},
              {bind: 'amountPaid', label: 'Amount Paid'},
              {bind: 'upiCard', label: 'If UPI/Card details'},
            ]
        });

        this.themeChange = this.themeChange.bind(this);
        this.tabsOnSelected = this.tabsOnSelected.bind(this);
        this.submit = this.submit.bind(this);
        this.open = this.open.bind(this);
        this.onWindowInit = this.onWindowInit.bind(this);
        this.refreshForm = this.refreshForm.bind(this);
        this.onLoad = this.onLoad.bind(this);
        this.amountUpdate = this.amountUpdate.bind(this);
        this.totalAmountUpdate = this.totalAmountUpdate.bind(this);
        this.makeMultiSelectCombo = this.makeMultiSelectCombo.bind(this);
        Common.subscribe(Common.WATCH.THEME, this.themeChange);
    }

    themeChange(newValue){
        this.theme = newValue;
    }

    tabsOnSelected(event) {
        if(event.type === 'selected'){
            this.selectedTab = this.tabRef.current.val();

            this.forceUpdate();
            this.refreshForm();
        }
    }

    amountUpdate(value, fieldId){       
        if(value != null){
            var config = {};
            config[fieldId] = 0;

            value.forEach((element)=>{
                config[fieldId] += (element.fee * 1);
            })

            Common.fieldUpdate(this.paymentTemp, this.paymentRef.current, config, fieldId);
        }
    }

    totalAmountUpdate(fields, fieldId){       
        if(fields != null){
            var config = {};
            config[fieldId] = 0;

            fields.forEach((element)=>{
                let row = Common.getFormRow(this.paymentTemp, element);
                config[fieldId] += (row.inputValue != null ? (row.inputValue) * 1: 0);
            });

            Common.fieldUpdate(this.paymentTemp, this.paymentRef.current, config, fieldId);
        }
    }

    refreshForm(){
        if(this.selectedTab == 0){
            this.appointmentRef.current.refresh();      
            Common.loopInput(this.appointmentTemp, this.appointmentRef.current, Common.updateDisable, {});      
        } else {
            this.paymentRef.current.refresh();
            Common.loopInput(this.paymentTemp, this.paymentRef.current, Common.updateDisable, {});
        }
    }

    submit(){
        var index = this.tabRef.current.val();
    
        if(index === 1){
          var appointmentDetail = {};

          Common.loopInput(this.appointmentTemp, this.appointmentRef.current, Common.getValue, appointmentDetail);
          Common.loopInput(this.paymentTemp, this.paymentRef.current, Common.getValue, appointmentDetail);
         
          HttpAJAX.POST('/api/patient/appointment/book', appointmentDetail, (data)=>{
            console.log(data, appointmentDetail);
            this.close();  
          }, {message: 'The patient detail has been updated successfully.'});
          //this.open(false);      
        }
        else {
          this.tabRef.current.select(++index);
        }
    }

    open(visible){
        this.visibile = visible;

        if(visible){
            this.window.current.open();
            this.tabRef.current.select(0);
        }else {
          this.window.current.close();
        }

        this.forceUpdate();
    }

    onWindowInit(){
        this.tabRef.current.setOptions({reorder: true});
        this.window.current.focus();
    }

    makeMultiSelectCombo(event){
        event.jqxDropDownList({checkboxes: true, enableSelection: true, source: this.options, width: 354});
        event.jqxDropDownList().select((data)=>{
            if(this.value == null){
                this.value = [];
            }
            if(this.selectedIndex == null){
                this.selectedIndex = [];
            }
            if(data.args.item.checked){
                this.value.push(data.args.item.originalItem);
                this.selectedIndex.push(data.args.item.index);
            } else{
                this.value = this.value.filter((ele)=>{
                    return (ele.id != data.args.item.originalItem.id);
                })
                this.selectedIndex = this.selectedIndex.filter((ele)=>{
                    return (ele != data.args.item.index);
                })
            }
            
            this.change(this.value);
        });

        if(this.selectedIndex != null && this.selectedIndex.length > 0){
            this.selectedIndex.forEach((element) =>{
                event.jqxDropDownList('checkIndex', element)
            })
        }

        return event;
    }

    onLoad(){
        HttpAJAX.GET('/api/v1/doctors', (data) => {
            let row = Common.getFormRow(this.paymentTemp, 'consultationDoctor');

            if(row != null && data != null){
                let source = [];

                data.forEach((element) => {
                    source.push({value: element.name, id: element.id, fee: element.fee});
                });

                row.options = source;
            }
        })

        HttpAJAX.GET('/api/v1/diagnostic', (data) => {
            let row = Common.getFormRow(this.paymentTemp, 'diagnostics');

            if(row != null && data != null){
                let source = [];

                data.forEach((element) => {
                    source.push({value: element.diagnosticName, id: element.id, fee: element.fee});
                });

                row.options = source;
            }
        })

        HttpAJAX.GET('/api/v1/service', (data) => {
            let row = Common.getFormRow(this.paymentTemp, 'services');
            let source = [];

            if(data != null){
                data.forEach((element) => {
                    source.push({value: element.serviceName, id: element.id, fee: element.fee});
                });
            }

            if(row != null){
                row.options = source;
            }

            if(row != null){
                row.options = source;
            }
        })
    }

    componentDidMount(){
        this.open(false);
        this.onLoad();
        this.refreshForm();
    }

    render() {
        return(
            <div className='scAppointment' style={{height: 'auto'}}>
                <JqxWindow ref={this.window}
                    width={this.formWidth}
                    height={520}
                    theme={this.theme}
                    isModal={true}
                    autoOpen={false}
                    resizable={false}
                    minWidth={200} maxWidth={1200}
                    minHeight={200} maxHeight={600}
                    initContent={this.onWindowInit}
                    showCollapseButton={true}
                >
                    <div style={{minHeight: 24}} className='scCenterXY scTitleAlign'>
                        <span className='scCenterXY'>
                            <FaDesktop color='red' /><span style={{padding: '0px 5px'}}>Appointment</span>
                        </span>
                    </div>
                    <div style={{ overflow: 'hidden', minHeight: '300px' }}>
                        <JqxTabs ref={this.tabRef} width={'100%'} height={'100%'} theme={this.theme} onSelected={(event)=> this.tabsOnSelected(event)}>
                            <ul>
                                <li>Appointment</li>
                                <li>Payment</li>
                            </ul>
                            <div>
                                <JqxForm ref={this.appointmentRef} width={'100%'} height={"100%"} theme={this.theme} template={this.appointmentTemp}/>        
                            </div>
                            <div>
                                <JqxForm ref={this.paymentRef} width={'100%'} height={"100%"} theme={this.theme} template={this.paymentTemp}/>
                            </div>                            
                        </JqxTabs>
                        <div className='scFooter scCenterXY'>   
                            <div className='scFooterContent'>
                                <JqxButton theme={this.theme} template={'primary'} width={80} style={{ float: 'left', marginLeft: '4px', cursor: 'pointer' }} onclick={this.submit}>
                                    {
                                        this.selectedTab === 1 ? 'Submit' : 'Next'
                                    }
                                </JqxButton>
                                <JqxButton theme={this.theme} template={'primary'} width={80} style={{ float: 'left', marginLeft: '4px', cursor: 'pointer' }} onclick={()=> this.open(false)}>Cancel</JqxButton>
                            </div>
                        </div>
                    </div>
                </JqxWindow>
            </div>
        )
    }
}

export default Appointment;