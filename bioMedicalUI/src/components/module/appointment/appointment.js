import React, { Component } from 'react';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxWindow from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxwindow';
import JqxTabs  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxtabs';
import JqxForm from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxform';

import { FaDesktop } from "react-icons/fa";

import HttpAJAX from '../../service/httpAJAX.js';
import Common from '../../service/common.js';
import '../../page/page.css';
import './appointment.css';

export class Appointment extends Component {
    visibile = false;
    selectedTab = 0;
    formWidth = 600;
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
              {bind: 'consultationDoctor', label: 'Consultation-Doctor', type: 'option',component: 'jqxDropDownList', init: this.convertCombo},
              {bind: 'diagnostics', label: 'Diagnostics'},
              {bind: 'services', label: 'Services'},
              {bind: 'totalAmount', label: 'Total Amount'},
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
        this.convertCombo = this.convertCombo.bind(this);
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

    refreshForm(){
        if(this.selectedTab == 0){
            this.appointmentRef.current.refresh();
        } else {
            this.paymentRef.current.refresh();
        }
    }

    submit(){
        var index = this.tabRef.current.val();
    
        if(index === 1){
          var appointmentDetail = {payment: {}};

          Common.loopInput(this.appointmentTemp, this.appointmentRef.current, Common.getValue, appointmentDetail);
          Common.loopInput(this.paymentTemp, this.paymentRef.current, Common.getValue, appointmentDetail.payment);
         
          console.log(appointmentDetail);
          this.open(false);      
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

    convertCombo(event){
        if(this.options != null){
            event.jqxDropDownList({checkboxes: true, enableSelection: true, source: this.options, width: 256});
        }
    }

    onLoad(){
        HttpAJAX.GET('/api/v1/doctors', (data) => {
            let row = Common.getFormRow(this.paymentTemp, 'consultationDoctor');

            if(row != null && data != null){
                let source = [];

                data.forEach((element) => {
                    source.push({value: element.name, key: element.id, fee: element.fee});
                });

                row.options = source;
            }
        }, (error)=> {
          console.log(error);
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
                    height={450}
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