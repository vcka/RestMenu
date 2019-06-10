var $ = require('jquery');
var role = null;
var origin  = location.host;
//console.log(origin);
var table = `<table class="table table-hover table-borderless">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col" style="display:none;">#</th>
                    <th scope="col" class="text-center">Dishes</th>
                  </tr>
             </thead>`;

$.ajax({
  url: 'http://' + origin + '/api/params',
  async: false,
  success: function(data) {
    role = data.username;
    if (role == "admin") {
      $('#login').attr("href", "/logoff");
      $('#login').text("Logout");
    }
  }
});

$.ajax({
  url: 'http://' + origin + '/api/items',
  async: false,
  success: function(data) {

    $.each(data, function(key, value) {
      table += ('<tr>');
      table += ('<th scope="row" style="display:none;">' + value.id + '</th>');
      table += ('<td><img src="images/dish.png" alt="" height="50" width="50" style="margin-right: 30px;"><span>' + value.name + '</span>');

      table += ('</td></tr>');
    });
    table += '</table>';
    $('#dishesList').html(table);
    if (role == "admin") {
      $('#dishesList').prepend(`<button id="add" class="btn btn-danger">Add menu item</button>`);
    }
  }
});

$('#dishesList').on('click', 'td', function() {
  var id = $(this).siblings().text();
  $.getJSON('http://' + origin + '/api/items/' + id, function(data) {
    if (role == "admin") {
      $('.modal-body').html(`<form>
        <div class="form-group">
          <label for="recipient-name" class="col-form-label">Name:</label>
          <input type="text" class="form-control" id="recipient-name" value="${data.name}">
        </div>
        <div class="form-group">
          <label for="message-text" class="col-form-label">Description:</label>
          <textarea class="form-control" id="message-text">${data.description}</textarea>
        </div>
      </form>`);
    } else {
      $('#description').html(data.description);
      $('#comments').html("");
      $.each(data.comments, function(key, value) {
        $('#comments').prepend(`<div class="dropdown-divider"></div>
                                <p>${value.comment}</p>`);
      });
      $('#comments').append(`<input placeholder="Comment" type="text" style="height:30px" class="form-control"/>
                             <br>
                             <span class="text-mute">Please write your opinion.</span>
                             <button value="${data.id}" class="btn btn-sm btn-outline-danger pull-right">Save</button>`)
    }
  });
  if (role == "admin") {
    $('.modal-footer').html(`<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                             <button id="delete" value="${id}" class="btn btn-danger">Delete</button>
                             <button id="save" value="${id}" class="btn btn-primary">Save</button>`);
//    $('.modal-footer').append(`<button id="delete" value="${id}" class="btn btn-danger">Delete</button>`);
//    $('.modal-footer').append(`<button id="save" value="${id}" class="btn btn-primary">Save</button>`);
  }
  $('#exampleModal').modal('show');

});

$('#comments').on('click', 'button', function() {
  var id = ($(this).val());
  var msg = $('#comments input').val();
  var data = {
    "comment": msg
  };
  $.ajax({
    url: 'http://' + origin + '/api/comment/' + id,
    contentType: 'application/json',
    type: 'PUT',
    data: JSON.stringify(data),
    success: function(data) {
      $.getJSON('http://' + origin + '/api/items/' + id, function(data) {
        $('#description').html(data.description);
        $('#comments').html("");
        $.each(data.comments, function(key, value) {
          $('#comments').prepend(`<div class="dropdown-divider"></div>
                                 <p>${value.comment}</p>`);
        });
        $('#comments').append(`<input placeholder="Comment" type="text" style="height:30px" class="form-control"/>
                               <br>
                               <span class="text-mute">Please write your opinion.</span>
                               <button value="${data.id}" class="btn btn-sm btn-primary pull-right">Save</button>`);
      });
    }
  });
});

$('.modal-footer').on('click', '#delete', function() {
  var id = ($(this).val());
  $.ajax({
    url: 'http://' + origin + '/api/remove/' + id,
    type: 'DELETE',
    success: function(data) {
      $('#exampleModal').modal('hide');
      location.reload();
    }
  });
});

$('.modal-footer').on('click', '#save', function() {
  var id = ($(this).val());
  var name = ($('#recipient-name').val());
  var description = ($('#message-text').val());
  var data = {
    "id": id,
    "name": name,
    "description": description
  };
  $.ajax({
    url: 'http://' + origin + '/api/edit/' + id,
    contentType: 'application/json',
    type: 'PUT',
    data: JSON.stringify(data),
    success: function(data) {
      $('#exampleModal').modal('hide');
      location.reload();
    }
  });
});

$('#dishesList').on('click', '#add', function() {
  $('#addModal').modal('show');
})

$('.modal-footer').on('click', '#addItem', function() {
  var name = ($('#recipient-name').val());
  var description = ($('#message-text').val());
  var data = {
    "name": name,
    "description": description
  };
  $.ajax({
    url: 'http://' + origin + '/api/add/',
    contentType: 'application/json',
    type: 'POST',
    data: JSON.stringify(data),
    success: function(data) {
      $('#addModal').modal('hide');
      location.reload();
    }
  });
});
