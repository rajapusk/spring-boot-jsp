import React, { Component } from 'react';
import './App.css';

import Patient  from './components/module/patient/patient.js';
import 'jqwidgets-scripts/jqwidgets/styles/jqx.base.css';
import 'jqwidgets-scripts/jqwidgets/styles/jqx.material.css';
import Common from './components/service/common.js';

export class App extends Component {   
    constructor(props:any) {
		super(props);
        Common.setValue(Common.WATCH.THEME, 'material');
    }
	
    render() {
        return (			
			<div className="scApp">
                <div className='scCenterXY scTitle'>
                    Patient Details
                </div>
                
                <div className="scContent">
                    <Patient></Patient>
                </div>
            </div>
        );
    }
}

export default App;