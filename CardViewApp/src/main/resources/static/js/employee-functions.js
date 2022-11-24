
//Function to initialize some of the basic functionalities on load of the page
function initListeners(){
    //Onclick of 'Browse' button
    document.getElementById("inputGroupFile02").onchange = function(){

    // Allowing file types
    var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

    if (!allowedExtensions.exec(this.value)) {
        alert('Invalid file type');
        this.value = '';
        return false;
    }else{
        var x = document.getElementsByClassName("imgContainer");
        for (var i=0; i < x.length; i++) {
            if(x[i].id == (document.getElementById("modal-id").value+"_"+this.files.item(0).name)){
                alert("File with the same name already exists.")
                return false;
            }
        }
    }
    document.getElementById("inputGroupFile02_val").innerHTML = this.files.item(0).name;
}

    //Onclick of 'Upload' button
    document.getElementById("inputGroupFile03").onclick = function(){
        if(document.getElementById("inputGroupFile02").value == null || document.getElementById("inputGroupFile02").value == ""){
            alert('Please select a file to upload');
            return;
        }
        var file    = document.getElementById("inputGroupFile02").files[0];
        var reader  = new FileReader();
        var modalId = document.getElementById("modal-id").value;

        //Create image thumbnail while upload
        var thumbnailId = "preview_"+file.name;
        document.getElementById("attachmentThumbnails").innerHTML += ""+
        "<div class=\"imgContainer\" id=\'"+modalId+"_"+file.name+"\'>"+
        "<img class=\"thumbnail\"id=\'"+thumbnailId+"\' src=\"\" alt=\"\" style=\"display:none\"></img>"+
        "<div class=\"overlay\" onclick=\"removeAttachment(\'"+modalId+"_"+file.name+"\')\"><a href=\"#\" class=\"removeIcon\"><i class=\"bi bi-x-circle\"></i></a></div>"+
        "</div>";
        var preview = document.getElementById(thumbnailId);
        reader.onloadend = function () {
            preview.src = reader.result;
            preview.style.display = "block";
        }
        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.src = "";
        }

        //Ajax call to upload files to the server and save it in the database
        var myFormData = new FormData();
        myFormData.append('pictureFile', file);
        myFormData.append('id', modalId);
        $.ajax({
          url: '/CardView/employee/uploadFile',
          type: 'POST',
          processData: false,
          contentType: false,
          dataType : 'json',
          data: myFormData,
          success: function(data) {
            // Do something after Ajax completes
          }
        });
        document.getElementById("inputGroupFile02").value = "";
        document.getElementById("inputGroupFile02_val").innerHTML = "";
    }

    //Called on click of the card to edit
    $('#exampleModal').on('show.bs.modal', function (event) {
      var button = $(event.relatedTarget) // Button that triggered the modal
      // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
      // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
      var modal = $(this)
      //modal.find('.modal-title').text('New message to ' + recipient)
      modal.find('#modal-id').val(button.data('id'))
      modal.find('#exampleModalLabel').text(button.data('firstname')+' '+button.data('lastname'))
      modal.find('#modal-firstname').val(button.data('firstname'))
      modal.find('#modal-lastname').val(button.data('lastname'))
      modal.find('#modal-dateofbirth').val(button.data('dateofbirth'))

      //Select radio button based on the value during edit
      var x = document.getElementsByClassName('class-modal-gender');
      for (var i=0; i < x.length; i++) {
          if(x[i].value == button.data('gender')){
            x[i].checked = true;
            modal.find('#modal-gender').val(button.data('gender'));
            break;
          }
      }

      modal.find('#modal-country').val(button.data('country'))
      modal.find('#modal-expired').attr('checked',button.data('expired'))

      //Based on the attachements, thumbnails are created and displayed during edit
      document.getElementById("attachmentThumbnails").innerHTML = "";
      if(button.data('attachments') != null && button.data('attachments').length > 0){
        var attachments = button.data('attachments').split("|");
        for(var i=0; i<attachments.length; i++){
            if(attachments[i].trim().length > 0){
                var modalId = button.data('id');
                var thumbnailId = "preview_"+attachments[i];
                document.getElementById("attachmentThumbnails").innerHTML += ""+
                "<div class=\"imgContainer\" id=\'"+attachments[i]+"\'>"+
                "<img class=\"thumbnail\"id=\'"+thumbnailId+"\' src=\"images/"+attachments[i]+"\" alt=\"\" style=\"display:block;\"></img>"+
                "<div class=\"overlay\" onclick=\"removeAttachment(\'"+attachments[i]+"\')\"><a href=\"#\" class=\"removeIcon\"><i class=\"bi bi-x-circle\"></i></a></div>"+
                "</div>";
            }
        }
      }
    })

    //Adding event listener on change of gender value using radio-button which will then assign the value
    var x = document.getElementsByClassName('class-modal-gender');
    for (var i=0; i < x.length; i++) {
        x[i].addEventListener('change', setGender);
    }

    //Assign function to update button
    $('#updateButton').on('click', function (event) {
        updateAndSubmit();
    })
}

//Function to submit the form with all the values
function updateAndSubmit(){
    var x = document.getElementsByClassName("imgContainer");
    var splitKey = "|";
    var y = "";
    for (var i=0; i < x.length; i++) {
        y += x[i].id+splitKey;
    }
    console.log(y);
    document.getElementById("attachments").value = y;
    document.getElementById("employeeForm").submit();
}

//Function to set gender value
function setGender(t) {
    document.getElementById("modal-gender").value = this.value;
}

//Function to remove attachments on hover and click on the thumbnail during edit
function removeAttachment(id){
    var myFormData = new FormData();
    myFormData.append('filename', id);
    $.ajax({
      url: '/CardView/employee/deleteFile',
      type: 'POST',
      processData: false,
      contentType: false,
      dataType : 'json',
      data: myFormData,
      success: function(data) {
        // Do something after Ajax completes
      }
    });
    var element = document.getElementById(id);
    element.parentNode.removeChild(element);
}