
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Uploading Files Example with Spring Boot, Freemarker</title>
    </head>

    <body onload="updateSize();">
        <form name="uploadingForm" enctype="multipart/form-data"  method="POST">
            <p>
                <input id="fileInput" type="file" name="uploadingFiles" onchange="updateSize();" multiple>
                selected files: <span id="fileNum">0</span>;
                total size: <span id="fileSize">0</span>
            </p>
            <p>
                <input type="submit" value="Upload files">
            </p>
        </form>
        <form id="post">
                    <p>
                        name: <input type="text" name="name"><br>
                        description: <input type="text" name="description"><br>
                    </p>
                    <p>
                        <input type="submit" value="Add item">
                    </p>
                </form>

        <span id="response"><span>
        <div>
            <div>Uploaded files:</div>
            <#list files as file>
            <div>
            ${file.getName()}
            </div>
            </#list>
        </div>
        <!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script>
            function updateSize() {
                var nBytes = 0,
                        oFiles = document.getElementById("fileInput").files,
                        nFiles = oFiles.length;
                for (var nFileId = 0; nFileId < nFiles; nFileId++) {
                    nBytes += oFiles[nFileId].size;
                }

                var sOutput = nBytes + " bytes";
                // optional code for multiples approximation
                for (var aMultiples = ["KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB"], nMultiple = 0, nApprox = nBytes / 1024; nApprox > 1; nApprox /= 1024, nMultiple++) {
                    sOutput = nApprox.toFixed(3) + " " + aMultiples[nMultiple] + " (" + nBytes + " bytes)";
                }
                // end of optional code

                document.getElementById("fileNum").innerHTML = nFiles;
                document.getElementById("fileSize").innerHTML = sOutput;

                var basicInfo = JSON.stringify(
                    {
                        name : 'makas',
                        description : 'kakas'
                    });

                            $.ajax({
                                url: '/api/add',
                                dataType: 'text',
                                type: 'post',
                                contentType: 'application/json',
                                data: basicInfo,
                                success: function( data, textStatus, jQxhr ){
                                    $('#response').html( data );
                                },
                                error: function( jqXhr, textStatus, errorThrown ){
                                    alert( errorThrown );
                                }
                            });

                            $(document).ready(function (e) {
                             $("#fileInput").on('submit',(function(e) {
                              e.preventDefault();
                              $.ajax({
                                     url: "/api/upload",
                               type: "POST",
                               data:  new FormData(this),
                               contentType: false,
                                     cache: false,
                               processData:false,
                               beforeSend : function()
                               {
                                //$("#preview").fadeOut();
                                $("#err").fadeOut();
                               },
                               success: function(data)
                                  {
                                if(data=='invalid')
                                {
                                 // invalid file format.
                                 $("#err").html("Invalid File !").fadeIn();
                                }
                                else
                                {
                                 // view uploaded file.
                                 $("#preview").html(data).fadeIn();
                                 $("#form")[0].reset();
                                }
                                  },
                                 error: function(e)
                                  {
                                  alert(error);
                                $("#err").html(e).fadeIn();
                                  }
                                });
                             }));
                            });


            }
        </script>

    </body>
</html>