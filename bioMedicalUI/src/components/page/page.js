import React, { Component } from 'react';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxWindow from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxwindow';
import JqxGrid,  { jqx }  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxgrid';

import { FaDesktop } from "react-icons/fa";

import Common from '../service/common.js';
import IconSVG from '../service/iconSVG.js';
import './page.css';

export class Page extends Component { 
  theme = '';
  buttonStyle = { float: 'left', marginLeft: '4px', cursor: 'pointer' };
  source = {
      datatype: 'array',
      datafields: null,
      localdata: null
  };
  
  iconCell = function(column){
		var iconRender = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
      if(column.icon != null){
        return IconSVG.getIcon(column.icon);
      }
        
      return IconSVG.getIcon(IconSVG.ICON.PEN);
    }

    if(column.width == null){
			column.width = 40;
		}

    column.text = '';
    column.datafield = column.icon;
    column.columntype = 'string';
    column.editable = false;
    column.groupable = false;
    column.draggable = false;
    column.resizable = false;
    column.cellsrenderer = iconRender;
	}

  snoCell(){
		var cellRender = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
	    return "<div class='scCenterXY scFullHeight'>" + (value + 1) + "</div>";
	  }
	    		
		return {
			text: '#', sortable: false, filterable: false, editable: false, groupable: false, draggable: false, resizable: false, cellsalign: 'center',
			datafield: '#', columntype: 'number', width: 50, cellsrenderer: cellRender
		}
	}

  constructor(props){
    super(props);
    this.pgGrid = React.createRef();
    this.window = React.createRef();
    this.source.datafields = props.grid.datafields;
    this.dataAdapter = new jqx.dataAdapter(this.source);

    if(props.grid != null && props.grid.columns != null){
      let srnCell = this.snoCell();

      props.grid.columns.splice(0, 0, srnCell);
      props.grid.columns.forEach((element) => {
        if(element.iconColumn){
          this.iconCell(element);
        }
      });
    }

    this.newHandler = this.newHandler.bind(this);
    this.themeChange = this.themeChange.bind(this);
    this.open = this.open.bind(this);
    this.setRows = this.setRows.bind(this);
    this.onWindowInit = this.onWindowInit.bind(this);
    Common.subscribe(Common.WATCH.THEME, this.themeChange);
  }

  themeChange(newValue){
    this.theme = newValue;
  }

  newHandler(){
    console.log('Called newHandler')
  }

  open(visible){
    if(visible){
      this.window.current.open();
    }else {
      this.window.current.close();
    }
  }

  setRows(rows){
    this.source.localdata = rows;
    this.forceUpdate();
  }
  
  onWindowInit(){
    if(this.props.windowInit != null){
      this.props.windowInit();
      this.window.current.focus();
    }
  }

  componentDidMount(){
    this.open(false);
  }

  render() {
    let button;
    if (this.props.grid.showNew) {
      button = <JqxButton template={'primary'} theme={this.theme} width={80} style={this.buttonStyle} onClick={() => { console.log("button clicked");}}>New</JqxButton>;
    }

    return(
        <div>
            <JqxWindow ref={this.window}
              width={this.props.window.width}
              height={this.props.window.height}
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
                  <FaDesktop color='red' /><span style={{padding: '0px 5px'}}>{this.props.window.title}</span>
                </span>
              </div>
              <div style={{ overflow: 'hidden', minHeight: 200 }}>
                {this.props.window.content}
              </div>
            </JqxWindow>
            <div className="scCenterXY scPgButtonBox">
              <div className='scPgButtons'>
                {button}
                {
                  this.props.buttons.map((ele, index)=>{
                    return <JqxButton key={index} theme={this.theme} template={'primary'} width={80} style={this.buttonStyle} onClick={()=> ele.click()}>{ele.label}</JqxButton>
                  })
                }
              </div>
            </div>
            <div className="scPgGridBox">
              <JqxGrid ref={this.pgGrid}
                  theme={this.theme}
                  width={'100%'} height={'100%'} source={this.dataAdapter} columns={this.props.grid.columns}
                  pageable={true} autoheight={false} sortable={true}
                  altrows={true} enabletooltips={true} editable={false}
                  onCellclick={this.props.onCellclick} 
                  selectionmode={'singlecell'}
              />
            </div>
        </div>
    )
  }
}
export default Page;