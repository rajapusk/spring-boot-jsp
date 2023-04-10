import React, { Component } from 'react';
import JqxTabs  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxtabs';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxForm from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxform';

import Appointment from '../appointment/appointment.js';
import Common from '../../service/common.js';
import IconSVG from '../../service/iconSVG.js';
import HttpAJAX from '../../service/httpAJAX.js';
import Page  from '../../page/page.js';
import Avatar  from '../../avatar/avatar.js';
import Record  from '../../record/record.js';
import './patient.css';

export class Patient extends Component {
  theme = '';
  buttons;
  grid;
  window;
  editRow;
  formWidth = 800;
  labelWidth = 180;
  selectedTab= 0;
	controlWidth = ((this.formWidth / 2) - this.labelWidth);

  constructor(props){
    super(props);
    this.avatarRef = React.createRef();
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
        { text: 'Mobile Number', datafield: 'mobileNumber', width: 150, cellsalign: 'right' },
        //{ iconColumn: true, icon: IconSVG.ICON.EYE},
        { iconColumn: true, icon: IconSVG.ICON.PEN},
        { iconColumn: true, icon: IconSVG.ICON.BUFFER},
        //{ iconColumn: true, icon: IconSVG.ICON.REMOVE},
      ],
    };

    this.window = {
      title: 'Add new patient',
      width: this.formWidth,
      height: 580,
      content: null
    }

    this.nextOfKin = [
      {bind: 'name', label: 'Name', value: '', dependentField: ['relation', 'mobile']},
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
        {bind: 'city', label: 'City', required: true},
        {bind: 'district', label: 'District'},
        {bind: 'state', label: 'State'},
        {bind: 'pinCode', label: 'Pin Code', numberOnly: true},
      ]
    });

    this.patientTemp = Common.getColTemplate({
      noOfCols: 2,
      labelWidth:this.labelWidth,
      controlWidth:this.controlWidth,
      rows:[
        {bind: 'mrNo', label: 'MR No', disabled: true},
        {bind: 'motherName', label: 'Mother Name'},
        //{bind: 'dateOfOpVisit', label: 'Date of OP Visit', type: 'date', format: Common.FORMAT.DATE, dispFormat: Common.FORMAT.DISP_DATE, minDate: new Date()},
        //{bind: 'timeOfOpVisit', label: 'Time of OP visit', type: 'time', format: Common.FORMAT.TIME, dispFormat: Common.FORMAT.DISP_TIME, minDate: new Date()},
        {bind: 'firstName', label: 'First Name'},
        {bind: 'lastName', label: 'Last Name'},
        {bind: 'dob', label: 'DOB', type: 'date', format: Common.FORMAT.DATE, dispFormat: Common.FORMAT.DISP_DATE, maxDate: new Date(), change: (value)=> {
          this.fieldDOBUpdate(value)
        }},
        {bind: 'age', label: 'Age', disabled: true},
        {bind: 'mobileNumber', label: 'Mobile Number'},
        {bind: 'emailId', label: 'Email ID'}
      ]
    });

    this.themeChange = this.themeChange.bind(this);
    this.submit = this.submit.bind(this);
    this.tabsOnSelected = this.tabsOnSelected.bind(this);
    this.fieldDOBUpdate = this.fieldDOBUpdate.bind(this);
    this.onWindowInit = this.onWindowInit.bind(this);
    this.uploadFile = this.uploadFile.bind(this);
    Common.subscribe(Common.WATCH.THEME, this.themeChange);
  }
  
  themeChange(newValue){
    this.theme = newValue;
  }

  fieldDOBUpdate(value){
    let diff = Common.dateDiff(value, new Date());

    if(diff != null){
      Common.fieldUpdate(this.patientTemp, this.patientRef.current, {age: diff.year}, 'age');
    }
  }

  addNewHandler(){
    this.editRow = null;
    this.pageRef.current.open(true);
    this.tabRef.current.select(0);
    this.avatarRef.current.setPhoto('');

    Common.loopInput(this.patientTemp, this.patientRef.current, Common.viewMode, {viewMode: false});
    Common.loopInput(this.addressTemp, this.addressRef.current, Common.viewMode, {viewMode: false});
    this.recordRef.current.onViewModel(false);
    Common.loopInput(this.patientTemp, this.patientRef.current, Common.updateValue, {});
    Common.loopInput(this.addressTemp, this.addressRef.current, Common.updateValue, {});
    this.recordRef.current.setVal(null);
  }

  close(){
    this.pageRef.current.open(false);
  }

  onCellclick(event){
    let cellField = event.args.datafield;

    console.log(event.args.row);

    if(cellField === IconSVG.ICON.PEN || cellField === IconSVG.ICON.EYE){      
      this.editRow = Common.clone(event.args.row.bounddata);
      this.getPatientDetail(this.editRow.mrNo, cellField);
    } else if(cellField === IconSVG.ICON.BUFFER){
      this.appointmentRef.current.parentData(event.args.row.bounddata);
      this.appointmentRef.current.open(true);
    } else if(cellField === IconSVG.ICON.REMOVE){
      if(event.args.row.bounddata != null){
        this.removeRecord(event.args.row.bounddata.mrNo);
      }
    }
  }
  
  getPatientDetail(patientId, cellField){
    HttpAJAX.GET('/api/patient/' + patientId, (data) => {
      if(data != null){
        this.editRow = data;
        
        if(cellField == IconSVG.ICON.EYE){
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
          
          if(data.address != null){
            Common.loopInput(this.addressTemp, this.addressRef.current, Common.updateValue, data.address);
          }
          
          if(data != null && data.photo != null){
            this.avatarRef.current.setPhoto(data.photo);
          }
          
          this.pageRef.current.open(true);
          this.tabRef.current.select(0);
      }
    })
  };

  submit(){
    var index = this.tabRef.current.val();

    if(index === 2){
      var fileInput = document.querySelectorAll('input[type=file]');
      let validKin = true;

      if((fileInput != null && fileInput.length > 0) || this.editRow != null){
        let patientDetail = this.editRow;

        if(patientDetail == null){
          patientDetail ={address: {}, nextOfKin: []};
        }
  
        Common.loopInput(this.patientTemp, this.patientRef.current, Common.getValue, patientDetail);
        Common.loopInput(this.addressTemp, this.addressRef.current, Common.getValue, patientDetail.address);

        if(patientDetail.address.city != '' && patientDetail.address.city != null){
          patientDetail.nextOfKin = this.recordRef.current.val();
          
          if(patientDetail.nextOfKin != null){
            patientDetail.nextOfKin.forEach((kin)=>{
              if(kin.name != null && kin.name != '' && (kin.relation == '' || kin.relation == null || kin.mobile == null || kin.mobile == '')){
                validKin = false;
              }
            });
          }

          if(validKin){
            HttpAJAX.POST('/api/patient/create', patientDetail, (data)=>{
              this.uploadFile(data, fileInput);
            }, {message: 'The patient detail has been updated successfully.'});
          } else {
            let toast = {message: 'Please fill the next of kin required fields.', type: "error"};

            Common.setValue(Common.WATCH.NOTIFICATION, toast);
          }
        }
        else {
          let toast = {message: 'The city is required to submit the patient details.', type: "error"};

          Common.setValue(Common.WATCH.NOTIFICATION, toast);
        }
      }
      else {

      }
    }
    else {
      this.tabRef.current.select(++index);
    }
  }

  uploadFile = function(result, fileInput){
    if(fileInput != null && fileInput.length > 0){
      Common.uploadFiles({id: result.id, empCode: result.empcode, fileInput: fileInput, fileIndex: 0, url: '/api/patient/uploadFile', successHandler: ()=>{
        this.onLoad();
        this.close();  
      }});				
    } else {
        this.onLoad();
        this.close();  
    }
	}

  tabsOnSelected(event) {
    if(event.type === 'selected'){
      this.selectedTab = this.tabRef.current.val();

      this.forceUpdate();
    }
  }

  removeRecord(patientId){
    HttpAJAX.DELETE('/api/patient/delete/' + patientId, (data) => {
      console.log(data)
    }, {message: 'The patient detail has been removed successfully.'});
  }

  onLoad(){
    HttpAJAX.GET('/api/patient', (data) => {
      if(data == null){
        data = [];
      }

      this.pageRef.current.setRows(data);
    })
  }

  onWindowInit(){
    this.tabRef.current.setOptions({reorder: true});
  }

  componentDidMount(){
    Common.loopInput(this.patientTemp, this.patientRef.current, Common.updateDisable, {});
    Common.loopInput(this.addressTemp, this.addressRef.current, Common.updateDisable, {});
    this.onLoad();
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
          <Avatar ref={this.avatarRef} />
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
          <Page ref={this.pageRef} buttons={this.buttons} grid={this.grid} window={this.window} windowInit={this.onWindowInit} onCellclick={this.onCellclick}></Page>
          <Appointment ref={this.appointmentRef}/>
      </div>
    )
  }
}

export default Patient;