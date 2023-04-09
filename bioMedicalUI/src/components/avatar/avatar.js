import React, { Component } from 'react';
import { FaUserAlt, FaPen} from "react-icons/fa";
import JqxFileUpload from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxfileupload';
import './avatar.css';

function Photo({ url }) {
    if(url == null || url === ''){
        return <FaUserAlt size={48} color='#c7c7c7'/>
    } else {
        return <img className='scAvatar' src={url} alt=''></img>
    }
}

export class Avatar extends Component {
    url;

    constructor(props){
        super(props);

        this.fileRef = React.createRef();
        this.fileChooser = this.fileChooser.bind(this);
        this.onSelect = this.onSelect.bind(this);
        this.setPhoto = this.setPhoto.bind(this);
    }

    fileChooser(){
        let button = document.querySelector('.jqx-file-upload-button-browse');

        if(button != null){
            button.click();
        }
    }

    setPhoto(photo){
        this.url = photo;
        this.fileRef.current.cancelAll();
        this.forceUpdate();
    }

    onSelect(event){
        var fileInput = document.querySelectorAll('input[type=file]');        
        var reader = new FileReader();
        reader.onload = (e) => {
            const { result } = e.target;

            this.url = result;
            this.forceUpdate();
        }

        if(fileInput != null && fileInput.length > 0 && fileInput[0].files != null && fileInput[0].files.length > 0){
            reader.readAsDataURL(fileInput[0].files[0]);
        }
    }

    render() {
        return(
            <div className='scCenterXY scContainer'>                
                <div className="scAvatar scCenterXY">
                    <Photo url={this.url} />

                    <div className='scPencil scCenterXY' onClick={this.fileChooser}>
                        <FaPen size={12} color='#FFF'/>
                        <JqxFileUpload ref={this.fileRef} className='scHide' accept='image/*' onSelect={this.onSelect} style={{ float: 'left', margin: '5px' }} width={'calc(100% - 10px)'} fileInputName={'fileToUpload'} />
                    </div>
                </div>
            </div>
        )
    }
}

export default Avatar;