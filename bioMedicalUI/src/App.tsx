import React, { Component } from 'react';
import './App.css';

import 'jqwidgets-scripts/jqwidgets/styles/jqx.base.css';
import 'jqwidgets-scripts/jqwidgets/styles/jqx.material.css';
import JqxNotification, {INotificationProps} from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxnotification';
import JqxLoader, {ILoaderProps } from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxloader';
import Patient  from './components/module/patient/patient.js';
import Common from './components/service/common.js';

export class App extends Component {
    private theme:any;
    private notificationRef = React.createRef<JqxNotification>();
    private loaderRef = React.createRef<JqxLoader>();

    constructor(props:any) {
		super(props);
        document.title = 'Bio Medical';
        
        this.showToast = this.showToast.bind(this);
        this.themeChange = this.themeChange.bind(this);
        Common.subscribe(Common.WATCH.THEME, this.themeChange);
        Common.subscribe(Common.WATCH.NOTIFICATION, (value:any)=>{
            this.showToast(value);
        });
        Common.subscribe(Common.WATCH.HTTP_CALL, (value:any)=>{
            if(this.loaderRef != null && this.loaderRef.current != null){
                if(value){
                    this.loaderRef.current!.open();
                } else {
                    this.loaderRef.current!.close();
                }
            }
        });

        Common.setValue(Common.WATCH.THEME, 'material');
    }

    themeChange(newValue:any){
        this.theme = newValue;
    }

    showToast(value:any){
        setTimeout(()=> {
            let options:INotificationProps = {template: value.type, theme: this.theme};
        
            document.getElementById('notifyContainer')!.innerHTML = value.message;
            this.notificationRef.current!.setOptions(options);
            this.notificationRef.current!.open();
        }, 200);
    }

    render() {        
        return (
			<div className="scApp">
                <div className='scCenterXY scTitle'>
                    Patient Details
                </div>
                
                <div className="scContent">
                    <JqxLoader ref={this.loaderRef} width={60} height={60} text={''}/>
                    <JqxNotification ref={this.notificationRef}
                        width={'auto'} autoOpen={false} autoClose={true}
                        blink={false} icon={{ width: 25, height: 25, url: './../images/smiley.png', padding: 5 }}
                        closeOnClick={true} opacity={0.9} position={'top-right'}>
                        <div id="notifyContainer"/>
                    </JqxNotification>
                    <Patient></Patient>
                </div>
            </div>
        );
    }
}

export default App;