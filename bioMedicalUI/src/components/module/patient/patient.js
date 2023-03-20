import React, { Component } from 'react';
import JqxTabs  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxtabs';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxForm from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxform';

import Appointment from '../appointment/appointment.js';
import Common from '../../service/common.js';
import Page  from '../../page/page.js';
import Record  from '../../record/record.js';
import './patient.css';

export class Patient extends Component {
  theme = '';
  buttons;
  grid;
  window;
  formWidth = 800;
  labelWidth = 180;
  selectedTab= 0;
	controlWidth = ((this.formWidth / 2) - this.labelWidth);

  constructor(props){
    super(props);
    this.patientRef = React.createRef();
    this.addressRef = React.createRef();
    this.tabRef = React.createRef();
    this.recordRef = React.createRef();
    this.pageRef = React.createRef();
    this.appointmentRef = React.createRef();
    this.addNewHandler = this.addNewHandler.bind(this);
    this.close = this.close.bind(this);
    this.onCellclick = this.onCellclick.bind(this);

    this.buttons = [{id:'addNew', label: 'New', click: this.addNewHandler}];
    this.submitDisabled = true;
    this.grid = {
      showNew: false,
      dataFields: [
        { name: 'mrNo', type: 'string' },
        { name: 'firstName', type: 'string' },
        { name: 'lastName', type: 'string' },
        { name: 'age', type: 'string' },
        { name: 'mobileNumber', type: 'string' }
      ],
      columns: [
        { text: 'MR NO', datafield: 'mrNo', width: 200},
        { text: 'First Name', datafield: 'firstName' },
        { text: 'Last Name', datafield: 'lastName'},
        { text: 'Age', datafield: 'age', width: 100, cellsalign: 'right' },
        { text: 'Mobile Number', datafield: 'mobileNumber', width: 150, cellsalign: 'right' }
      ],
    };

    this.window = {
      title: 'Add new patient',
      width: this.formWidth,
      height: 520,
      content: null
    }

    this.nextOfKin = [
      {bind: 'name', label: 'Name', value: ''},
      {bind: 'relation', label: 'Relation', value: ''},
      {bind: 'mobile', label: 'Mobile', value: ''}
    ]

    this.addressTemp = Common.getColTemplate({
      noOfCols: 2,
      labelWidth:this.labelWidth,
      controlWidth:this.controlWidth,
      rows:[
        {bind: 'hNo', label: 'House number'},
        {bind: 'building', label: 'Building'},
        {bind: 'street', label: 'Street'},
        {bind: 'colony', label: 'Colony'},
        {bind: 'city', label: 'City'},
        {bind: 'district', label: 'District'},
        {bind: 'state', label: 'State'},
        {bind: 'pinCode', label: 'Pin Code'},
      ]
    });

    this.patientTemp = Common.getColTemplate({
      noOfCols: 2,
      labelWidth:this.labelWidth,
      controlWidth:this.controlWidth,
      rows:[
        {bind: 'mrNo', label: 'MR No', disabled: true},
        {bind: 'dateOfOpVisit', label: 'Date of OP Visit', type: 'datetime'},
        {bind: 'timeOfOpVisit', label: 'Time of OP visit', type: 'datetime'},
        {bind: 'firstName', label: 'First Name'},
        {bind: 'lastName', label: 'Last Name'},
        {bind: 'motherName', label: 'Mother Name'},
        {bind: 'dob', label: 'DOB', type: 'datetime'},
        {bind: 'age', label: 'Age'},
        {bind: 'mobileNumber', label: 'Mobile Number'},
        {bind: 'emailId', label: 'Email ID'}
      ]
    });

    this.themeChange = this.themeChange.bind(this);
    this.submit = this.submit.bind(this);
    this.tabsOnSelected = this.tabsOnSelected.bind(this);
    Common.subscribe(Common.WATCH.THEME, this.themeChange);
  }
  
  themeChange(newValue){
    this.theme = newValue;
  }

  addNewHandler(){
    this.pageRef.current.open(true);
    this.tabRef.current.select(0);

    Common.loopInput(this.patientTemp, this.patientRef.current, Common.viewMode, {viewMode: false});
    Common.loopInput(this.addressTemp, this.addressRef.current, Common.viewMode, {viewMode: false});
    this.recordRef.current.onViewModel(false);
    Common.loopInput(this.patientTemp, this.patientRef.current, Common.updateValue, {});
    Common.loopInput(this.addressTemp, this.addressRef.current, Common.updateValue, {});
    this.recordRef.current.setVal(null);
  }

  componentDidMount(){
    Common.loopInput(this.patientTemp, this.patientRef.current, Common.updateDisable, {});
    Common.loopInput(this.addressTemp, this.addressRef.current, Common.updateDisable, {});
    
    this.pageRef.current.setRows([{mrNo: '10000000', firstName: 'firstName 1', address:[{'hNo': '11222'}], nextOfKin: [{name: 'test'}, {relation: 'r2'}]}]);
  }

  close(){
    this.pageRef.current.open(false);
  }

  onCellclick(event){
    let cellField = event.args.datafield;

    if(cellField == 'icon_pen' || cellField == 'icon_eye'){      
      let data = event.args.row.bounddata;

      if(cellField == 'icon_eye'){
        Common.loopInput(this.patientTemp, this.patientRef.current, Common.viewMode, {viewMode: true});
        Common.loopInput(this.addressTemp, this.addressRef.current, Common.viewMode, {viewMode: true});
        this.recordRef.current.onViewModel(true);
      } else {
        Common.loopInput(this.patientTemp, this.patientRef.current, Common.viewMode, {viewMode: false});
        Common.loopInput(this.addressTemp, this.addressRef.current, Common.viewMode, {viewMode: false});
        this.recordRef.current.onViewModel(false);
      }

      Common.loopInput(this.patientTemp, this.patientRef.current, Common.updateValue, data);
      this.recordRef.current.setVal(data.nextOfKin);

      if(data.address != null && data.address.length > 0){
        Common.loopInput(this.addressTemp, this.addressRef.current, Common.updateValue, data.address[0]);
      }
      
      this.pageRef.current.open(true);
      this.tabRef.current.select(0);
    } else if(cellField == 'icon_buffer'){
      this.appointmentRef.current.open(true);
    }
  }
  
  submit(){
    var index = this.tabRef.current.val();

    if(index === 2){
      let patientDetail = {address: {}, nextOfKin: []};
      Common.loopInput(this.patientTemp, this.patientRef.current, Common.getValue, patientDetail);
      Common.loopInput(this.addressTemp, this.addressRef.current, Common.getValue, patientDetail.address);
      patientDetail.nextOfKin = this.recordRef.current.val();

      console.log(patientDetail);
      this.close();      
    }
    else {
      this.tabRef.current.select(++index);
    }
  }

  tabsOnSelected(event) {
    if(event.type === 'selected'){
      this.selectedTab = this.tabRef.current.val();

      this.forceUpdate();
    }
  }

  render() {
    let content = <div className="scPatient">
      <JqxTabs ref={this.tabRef} width={'100%'} height={'100%'} theme={this.theme} onSelected={(event)=> this.tabsOnSelected(event)}>
        <ul>
          <li>Patient details</li>
          <li>Address details</li>
          <li>Next of Kin</li>
        </ul>
        <div>
          <JqxForm ref={this.patientRef} width={'100%'} height={"100%"} theme={this.theme} template={this.patientTemp}/>        
        </div>
        <div>
          <JqxForm ref={this.addressRef} width={'100%'} height={"100%"} theme={this.theme} template={this.addressTemp}/>
        </div>
        <div>
         <Record ref={this.recordRef} model={this.nextOfKin}/>
        </div>
      </JqxTabs>
      <div className='scFooter scCenterXY'>   
        <div className='scFooterContent'>
          <JqxButton theme={this.theme} template={'primary'} width={80} style={{ float: 'left', marginLeft: '4px', cursor: 'pointer' }} onclick={this.submit}>
            {
              this.selectedTab === 2 ? 'Submit' : 'Next'
            }
          </JqxButton>
          <JqxButton theme={this.theme} template={'primary'} width={80} style={{ float: 'left', marginLeft: '4px', cursor: 'pointer' }} onclick={this.close}>Cancel</JqxButton>
        </div>
      </div>      
    </div>

    this.window.content = content;

    return(
      <div>
          <Page ref={this.pageRef} buttons={this.buttons} grid={this.grid} window={this.window} onCellclick={this.onCellclick}></Page>
          <Appointment ref={this.appointmentRef}/>
      </div>
    )
  }
}

export default Patient;