import React, { Component } from 'react';
import JqxButton from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxbuttons';
import JqxWindow from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxwindow';
import JqxGrid,  { jqx }  from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxgrid';

import { FaDesktop } from "react-icons/fa";

import Common from '../service/common.js';
import './page.css';

export class Page extends Component { 
  theme = '';
  buttonStyle = { float: 'left', marginLeft: '4px', cursor: 'pointer' };
  source = {
      datatype: 'array',
      datafields: null,
      localdata: null
  };
  
  iconCell = function(config){
		if(config.width == null){
			config.width = 40;
		}
		
		if(config.text == null){
			config.text = '';
		}
		
		if(config.datafield == null){
			config.datafield = 'icon_' + config.icon;
		}
		
		var iconRender = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
      if(config.icon != null){
        return Common.getIcon(config.icon);
      }
        
      return Common.getIcon('pen');
    }
			
    return { text: config.text, datafield: config.datafield, columntype: 'number', width: config.width, editable: false, groupable: false, draggable: false, resizable: false, cellsrenderer: iconRender};
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
      let eyeIcon = this.iconCell({width: 40, icon: 'eye'});
      let penIcon = this.iconCell({width: 40, icon: 'pen'});
      let buffIcon = this.iconCell({width: 40, icon: 'buffer'});
      let removeIcon = this.iconCell({width: 40, icon: 'remove'});
      props.grid.columns.splice(0, 0, srnCell);
      props.grid.columns.splice(props.grid.columns.length, 0, eyeIcon);
      props.grid.columns.splice(props.grid.columns.length, 0, penIcon);
      props.grid.columns.splice(props.grid.columns.length, 0, buffIcon);
      props.grid.columns.splice(props.grid.columns.length, 0, removeIcon);
    }

    this.newHandler = this.newHandler.bind(this);
    this.themeChange = this.themeChange.bind(this);
    this.open = this.open.bind(this);
    this.setRows = this.setRows.bind(this);
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
              autoOpen={false}
              minWidth={200} maxWidth={1200}
              minHeight={200} maxHeight={600}
              showCollapseButton={true}
            >
              <div className='scCenterXY scTitleAlign'>
                <span className='scCenterXY'>
                  <FaDesktop color='red' /><span style={{padding: '0px 5px'}}>{this.props.window.title}</span>
                </span>
              </div>
              <div style={{ overflow: 'hidden' }}>
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