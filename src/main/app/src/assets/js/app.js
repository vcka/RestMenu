var $ = require('jquery');
var table = ` <table class="table table-hover table-borderless">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col" style="display:none;">#</th>
                    <th scope="col" class="text-center">Dishes</th>
                  </tr>
                </thead>`;

// $.getJSON('http://localhost:8089/api/items', function(data) {
//   // console.log(data);
//   $.each(data, function(key, object){
//     console.log(object);
//     $.each(object, function(key, value){
//       // console.log(key + " " + value);
//       if (key=="comments"){
//         // console.log(value);
//         $.each(value, function(key, object){
//           console.log(object.comment);
//         })
//       }
//     })
//   })
// })

// $("#button").click(function(){

// $.ajax({
//   type: 'get',
//   url: "http://localhost:8089/api/items",
//   data: {
//     format: 'json'
//   },
//   dataType: 'json',
//   success: function(data) {
$.getJSON('http://localhost:8089/api/items', function(data) {
  $.each(data, function(key, value) {
    table += ('<tr>');
    table += ('<th scope="row" style="display:none;">' + value.id + '</th>');
    table += ('<td><img src="images/dish.png" alt="" height="50" width="50" style="margin-right: 30px;"><span>' + value.name + '</span></td>');
    // table += ('<td><img src="' + value.ImageURLs.Thumb + '"></td>');
    // table += ('<td>' + value.description + '</td>');
    table += ('</tr>');
  });
  table += '</table>';
  $('#dishesList').html(table);
  // }
});

$('#dishesList').on('click', 'td', function() {
  var id = $(this).siblings().text();
  $.getJSON('http://localhost:8089/api/items/' + id, function(data) {
    $('#description').html(data.description);
    $('#comments').html("");
    $.each(data.comments, function(key, value) {
      $('#comments').append(`<p>${value.comment}</p>
                             <div class="dropdown-divider"></div>`);
    });
    $('#comments').append(`<input placeholder="Comment" type="text" style="height:30px" class="form-control"/>
                           <br>
                           <span class="text-mute">Please write your opinion.</span>
                           <button value="${data.id}" class="btn btn-sm btn-primary pull-right">Save</button>`)
  });
  $('#exampleModal').modal('show');
});

$('#comments').on('click', 'button', function() {
  var id = ($(this).val());
  var msg = $('#comments input').val();
  var data = {"comment":msg};
  $.ajax({
    url: 'http://localhost:8089/api/comment/' + id,
    contentType: 'application/json',
    type: 'PUT',
    data: JSON.stringify(data),
    success: function(data) {
      $.getJSON('http://localhost:8089/api/items/' + id, function(data) {
        $('#description').html(data.description);
        $('#comments').html("");
        $.each(data.comments, function(key, value) {
          $('#comments').append(`<p>${value.comment}</p>
                                 <div class="dropdown-divider"></div>`);
        });
        $('#comments').append(`<input placeholder="Comment" type="text" style="height:30px" class="form-control"/>
                               <br>
                               <span class="text-mute">Please write your opinion.</span>
                               <button value="${data.id}" class="btn btn-sm btn-primary pull-right">Save</button>`)
      });
    }
  });
});
// $('#dishesList').on('click','th',function(){
//   var id = $(this).text();
//   console.log(id);
//   $('#exampleModal').modal('show');
// });
