
let serverApi = "http://localhost:8080/";
let address="";

$(document).ready(function () {
    localStorage.setItem("address", "");
    $('#btnSearch').click(function (event) {
        event.preventDefault();
        let callUrl = serverApi + 'getVinInfo/' + $('#inputVin').val();
        // console.log(callUrl);
        $.ajax({
            method: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            url: callUrl,
            success: function (data) {
                setTableVIN(data);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                // alert(xhr.status);
                // alert(thrownError);
            }
        });
    });
});

function setTableVIN(data){
    for (let i = 0; $("#vinRow" + i).val() !== undefined; i++) {
        $("#vinRow" + i).remove();
    }
    let type = "";
    let make = "";
    let address="";
    for (let i = 0; i < data.results.length; i++) {
        let row = $("#vinRow").clone();
        row.find("#vinRowValueId").text(data.results[i].valueId);
        row.find("#vinRowValue").text(data.results[i].value);
        row.find("#vinRowVariableId").text(data.results[i].variableId);
        row.find("#vinRowVariable").text(data.results[i].variable);
        row.attr("id", "vinRow" + i); //change row id
        row.appendTo("#vinList"); //add to template
        row.show();
        if (data.results[i].variableId == "39") {
            type =data.results[i].value;
        }
        if (data.results[i].variableId == "26") {
            make =data.results[i].value;
        }
        if (data.results[i].variableId == "31" || data.results[i].variableId == "75"
            || data.results[i].variableId == "76" || data.results[i].variableId == "77") {
            address += data.results[i].value + "+";
        }
    }
    setTableMakes(type);
    setTableModels(make);
    localStorage.setItem('address', address);
    // $("#map_frame_id").contentDocument.location.reload(true);
    document.getElementById("map_frame_id").contentDocument.location.reload(true);
}
function setTableMakes(type){
    let callUrl = serverApi + 'getMakes/' + type;
    // console.log(callUrl);
    $.ajax({
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        url: callUrl,
        success: function (data) {
            for (let i = 0; $("#makeRow" + i).val() != undefined; i++) {
                $("#makeRow" + i).remove();
            }
            for (let i = 0; i < data.results.length; i++) {
                let row = $("#makeRow").clone();
                row.find("#makeRowMakeId").text(data.results[i].makeId);
                row.find("#makeRowMakeName").text(data.results[i].makeName);
                row.find("#makeRowVehicleTypeId").text(data.results[i].vehicleTypeId);
                row.find("#makeRowVehicleTypeName").text(data.results[i].vehicleTypeName);
                row.attr("id", "makeRow" + i); //change row id
                row.appendTo("#makeList"); //add to template
                row.show();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert(xhr.status);
            // alert(thrownError);
        }
    });
}
function setTableModels(make){
    let callUrl = serverApi + 'getModels/' + make;
    // console.log(callUrl);
    $.ajax({
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        url: callUrl,
        success: function (data) {
            for (let i = 0; $("#modelRow" + i).val() != undefined; i++) {
                $("#modelRow" + i).remove();
            }
            for (let i = 0; i < data.results.length; i++) {
                let row = $("#modelRow").clone();
                row.find("#modelRowMakeId").text(data.results[i].make_ID);
                row.find("#modelRowMakeName").text(data.results[i].make_Name);
                row.find("#modelRowModelId").text(data.results[i].model_ID);
                row.find("#modelRowModelName").text(data.results[i].model_Name);
                row.attr("id", "modelRow" + i); //change row id
                row.appendTo("#modelList"); //add to template
                row.show();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert(xhr.status);
            // alert(thrownError);
        }
    });
}
