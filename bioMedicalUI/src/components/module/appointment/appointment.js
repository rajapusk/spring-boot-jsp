import React, { Component } from 'react';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxWindow from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxwindow';
import JqxTabs  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxtabs';
import JqxForm from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxform';
import JqxComboBox from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxcombobox';
import Record  from '../../record/record.js';

import { FaDesktop } from "react-icons/fa";

import HttpAJAX from '../../service/httpAJAX.js';
import Common from '../../service/common.js';
import '../../page/page.css';
import './appointment.css';

export class Appointment extends Component {
    visibile = false;
    parentModel;
    selectedTab = 0;
    formWidth = 800;
    labelWidth = 180;
    controlWidth = (this.formWidth / 2) - 54;
    
    constructor(props){
        super(props);

        this.window = React.createRef();
        this.tabRef = React.createRef();
        this.appointmentRef = React.createRef();
        this.consRecordRef = React.createRef();
        this.diaRecordRef = React.createRef();
        this.servRecordRef = React.createRef();
        this.paymentRef = React.createRef();

        let departmentOpts = [
            { value: '' },
            { value: 'Pediatrics' },
            { value: 'Neonatology' },
            { value: 'Obstetrics' },
            { value: 'Pediatric Surgery' },
        ];

        let visitTypeOpts = [ { value: '', id: -1 },{ value: 'Regular', id: 0 }, { value: 'Followup', id: 1 } ];
     
        this.appointmentTemp = Common.getColTemplate({
            noOfCols: 2,
            labelWidth:this.labelWidth,
            controlWidth:this.controlWidth,
            rows:[
                {bind: 'dateOfOpVisit', label: 'Date of OP Visit', type: 'date', format: Common.FORMAT.DATE, dispFormat: Common.FORMAT.DISP_DATE, minDate: new Date()},
                {bind: 'timeOfOpVisit', label: 'Time of OP visit', type: 'time', format: Common.FORMAT.TIME, dispFormat: Common.FORMAT.DISP_TIME, minDate: new Date()},
                {bind: 'department', label: 'Department', type: 'option',component: 'jqxDropDownList', options: departmentOpts},
                {bind: 'patientType', label: 'Patient Type', type:"option", component: 'jqxDropDownList', options: [ { value: '' }, { value: 'Consultation' }, { value: 'Diagnostics' } ]},
                {bind: 'referral', label: 'Referral', type:"option", component: 'jqxDropDownList', options: [ { value: '' }, { value: 'Yes' }, { value: 'No' } ]},
                //{bind: 'visitType', label: 'Visit Type', type:"option", component: 'jqxDropDownList', options: [ { value: '' },{ value: 'First Visit' }, { value: 'Review' } ]},
                {bind: 'payeeType', label: 'Payee Type', type:"option", component: 'jqxDropDownList', options: [ { value: '' }, { value: 'Self' }, { value: 'Insurance' } ]}
            ]
        });

        this.consultationRecord = [
            {bind: 'consultationDoctor', label: 'Consultation-Doctor', type: 'option', change: (value, row)=>{
                this.updateTotalAmount(row, value, ['consultAmount']);
            }},
            {bind: 'visitType', label: 'Visit Type', type:"option", options: visitTypeOpts},
            {bind: 'consultAmount', label: 'Amount', value: '', disabled: true}
        ]

        this.diagnosticRecord = [
            {bind: 'diagnostics', label: 'Diagnostics', type: 'option', change: (value, row)=>{
                this.updateTotalAmount(row, value, ['diagAmount']);
            }},
            {bind: 'visitType', label: 'Visit Type', type:"option", options: visitTypeOpts},
            {bind: 'diagAmount', label: 'Amount', value: '', disabled: true}
        ]

        this.serviceRecord = [
            {bind: 'services', label: 'Services', type: 'option', change: (value, row)=>{
                this.updateTotalAmount(row, value, ['serviceAmount']);
            }},
            {bind: 'visitType', label: 'Visit Type', type:"option", options: visitTypeOpts},
            {bind: 'serviceAmount', label: 'Amount', value: '', disabled: true}
        ]

        this.paymentTemp = Common.getColTemplate({
            noOfCols: 2,
            labelWidth:this.labelWidth,
            controlWidth:this.controlWidth,
            rows:[
              {bind: 'consultAmount', label: 'Consultation-Doctor Amount', disabled: true},
              {bind: 'diagAmount', label: 'Diagnostics Amount', disabled: true},
              {bind: 'serviceAmount', label: 'Services Amount', disabled: true},              
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
        this.updateTotalAmount = this.updateTotalAmount.bind(this);
        this.parentData = this.parentData.bind(this);
        this.makeMultiSelectCombo = this.makeMultiSelectCombo.bind(this);
        Common.subscribe(Common.WATCH.THEME, this.themeChange);
    }

    themeChange(newValue){
        this.theme = newValue;
    }

    tabsOnSelected(event) {
        if(event.type === 'selected'){
            this.selectedTab = this.tabRef.current.val();

            if(this.selectedTab == 4){
                let config = {totalAmount: 0};
                let fields = ['consultAmount', 'diagAmount', 'serviceAmount', 'totalAmount'];
                let consultation = this.consRecordRef.current.val();
                let diagnostics = this.diaRecordRef.current.val();
                let service = this.servRecordRef.current.val();

                this.calcAmount(config, consultation, 'consultAmount');
                this.calcAmount(config, diagnostics, 'diagAmount');
                this.calcAmount(config, service, 'serviceAmount');
                
                fields.forEach((field)=>{
                    Common.fieldUpdate(this.paymentTemp, this.paymentRef.current, config, field);
                });
            }

            this.forceUpdate();
            this.refreshForm();
        }
    }

    calcAmount(config, values, key){
        if(values != null){
            config[key] = 0;

            values.forEach((element) => {
                let value = element[key];

                config[key] += (value != null ? value : 0);
            });

            config.totalAmount += config[key];
        }
    }

    updateTotalAmount(rows, value, fields){       
        if(fields != null){
            fields.forEach((element)=>{
                let row = Common.getFormRow(rows, element);
                row.value = value.fee;

                if(row.componentRef != null && row.componentRef.current != null){
                    row.componentRef.current.setOptions({value: row.value});
                }
            });
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

    formRecords(data, field, values, picklist){
        if(values != null){
            values.forEach((element) => {
                let row = {};
                
                row.patientId = data.patientId;

                if(element[picklist] != null){
                    row.fee = element[picklist].fee; 
                    row.id = element[picklist].id; 
                }
                
                if(element.visitType != null){
                    row.visitType = element.visitType.id; 
                }

                data[field].push(row);
            });
        }
    }

    submit(){
        var index = this.tabRef.current.val();
    
        if(index === 4){
            var appointmentDetail = {consultationDoctor: [], diagnostics: [], services: [], patientId: (this.parentModel != null ? this.parentModel.mrNo : null)};
            Common.loopInput(this.appointmentTemp, this.appointmentRef.current, Common.getValue, appointmentDetail);
            let consultationDoctor = this.consRecordRef.current.val();
            let diagnostics = this.diaRecordRef.current.val();
            let service = this.servRecordRef.current.val();

            this.formRecords(appointmentDetail, 'consultationDoctor', consultationDoctor, 'consultationDoctor');
            this.formRecords(appointmentDetail, 'diagnostics', diagnostics, 'diagnostics');
            this.formRecords(appointmentDetail, 'services', service, 'services');
            Common.loopInput(this.paymentTemp, this.paymentRef.current, Common.getValue, appointmentDetail);
     
            HttpAJAX.POST('/api/patient/appointment/book', appointmentDetail, (data)=>{
                this.open(false);  
            }, {message: 'The patient detail has been updated successfully.'});
        }
        else {
          this.tabRef.current.select(++index);
        }
    }

    parentData(data){
        this.parentModel = data;
    }

    open(visible){
        this.visibile = visible;

        if(visible){
            this.window.current.open();
            this.tabRef.current.select(0);
            this.consRecordRef.current.reset();
            this.diaRecordRef.current.reset();
            this.servRecordRef.current.reset();
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
            if(data != null){
                let source = [];

                data.forEach((element) => {
                    source.push({value: element.name, id: element.id, fee: element.fee});
                });

                this.consRecordRef.current.setComboSource('consultationDoctor', source);
            }
        })

        HttpAJAX.GET('/api/v1/diagnostic', (data) => {
            if(data != null){
                let source = [];

                data.forEach((element) => {
                    source.push({value: element.diagnosticName, id: element.id, fee: element.fee});
                });

                this.diaRecordRef.current.setComboSource('diagnostics', source);
            }
        })

        HttpAJAX.GET('/api/v1/service', (data) => {
            let source = [];

            if(data != null && typeof data.forEach == 'function'){
                data.forEach((element) => {
                    source.push({value: element.serviceName, id: element.id, fee: element.fee});
                });
            }

            this.servRecordRef.current.setComboSource('services', source);
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
                                <li>Consultation Doctor</li>
                                <li>Diagnostics</li>
                                <li>Services</li>
                                <li>Payment</li>
                            </ul>
                            <div>
                                <JqxForm ref={this.appointmentRef} width={'100%'} height={"100%"} theme={this.theme} template={this.appointmentTemp}/>        
                            </div>
                            <div>
                                <Record ref={this.consRecordRef} model={this.consultationRecord}/>
                            </div>
                            <div>
                                <Record ref={this.diaRecordRef} model={this.diagnosticRecord}/>
                            </div>
                            <div>
                                <Record ref={this.servRecordRef} model={this.serviceRecord}/>
                            </div>
                            <div>
                                <JqxForm ref={this.paymentRef} width={'100%'} height={"100%"} theme={this.theme} template={this.paymentTemp}/>
                            </div>                            
                        </JqxTabs>
                        <div className='scFooter scCenterXY'>   
                            <div className='scFooterContent'>
                                <JqxButton theme={this.theme} template={'primary'} width={80} style={{ float: 'left', marginLeft: '4px', cursor: 'pointer' }} onclick={this.submit}>
                                    {
                                        this.selectedTab === 4 ? 'Submit' : 'Next'
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