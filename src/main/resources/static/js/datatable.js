const btn = document.getElementById('searchBtn');
const api_url = 'http://localhost:8088/api';

let database = [];
let table = new DataTable('#weather', {
    columns: [
        {data: 'id',
            visible: false},
        {data: 'name' },
        {data: 'region'},
        {data: 'country'},
        {data: 'lat'},
        {data: 'lon'},
        {data: 'timezone'},
        {data: 'local_time'},
        {data: 'last_updated'},
        {data: 'temp_c'},
        {data: 'temp_f'},
        {data: 'humidity'},
        {data: null,
            render: function (data, type, row, meta) {
                return ' <img src="' + data.icon + '" width="48px" height="48px"/><br>' + data.status
            }
        },
        {data: null,
            defaultContent: '<button type="submit" class="delete-Btn"><i class="fa-regular fa-trash-can"></i></button>'
        }
    ],
    rowId: 'id',
    columnDefs: [
        { targets: '_all', className: 'dt-center dt-body-center'},
        { orderable: false, targets: 13 }
    ],
    createdRow: function(row, data, dataIndex) {
        $(row).addClass("main-row");
    }
});

document.addEventListener('DOMContentLoaded', () => {

    fetch(api_url + '/getAllWeather')
        .then(response => response.json())
        .then(json => {
            json.forEach((data) => {
                database.push(data);
            });
            loadFromDatabase();
        })

})

btn.addEventListener('click', () => {
    addNewRow();
})

function loadFromDatabase() {

    database.forEach((data) => {
        const rowData = {
            id: data.id,
            name: data.name,
            region: data.region,
            country: data.country,
            lat: data.lat,
            lon: data.lon,
            timezone: data.timezone,
            local_time: data.local_time,
            last_updated: data.last_updated,
            temp_c: data.temp_c,
            temp_f: data.temp_f,
            humidity: data.humidity,
            status: data.weatherStatus,
            icon: data.weatherIcon
        };

        table.row.add(rowData).draw();
    })
}

function addNewRow() {

    const searchLocation = document.getElementById('searchLocation');
    const searchDays = document.getElementById('searchDays');

    const searchCriteria = {
        location: searchLocation.value,
        days: searchDays.value != null ? searchDays.value : 1,
        aqi: 'no',
        alerts: 'no'
    }
    $.ajax({
        url: api_url + '/getWeather',
        data: JSON.stringify(searchCriteria),
        contentType: 'application/json',
        type: 'POST',
        success: function (json) {

            const rowData = {
                id: json.id,
                name: json.name,
                region: json.region,
                country: json.country,
                lat: json.lat,
                lon: json.lon,
                timezone: json.timezone,
                local_time: json.local_time,
                last_updated: json.last_updated,
                temp_c: json.temp_c,
                temp_f: json.temp_f,
                humidity: json.humidity,
                status: json.weatherStatus,
                icon: json.weatherIcon
            };

            database.push(json);
            table.row.add(rowData).draw();
        },
        error: function (xhr, status, error) {
            console.error('AJAX Error:', status, error);
        }
    });

    searchLocation.value = "";
    searchDays.value = "";
}


$('#weather tbody').on('click', '.delete-Btn', function() {

    const tr = $(this).closest('tr');
    const row = table.row(tr);
    const id = tr.attr('id');
    const index = findIndex(id);

    //ajax call to remove record from database
    $.ajax({
        url: api_url + '/delete/' + id,
        type: 'DELETE',
        success: function (json) {
            if (this.status === 204) {
                database.splice(index, 1);
                table.row(tr).remove().draw();
            }
        },
        error: function(xhr, status, error) {
            console.error('AJAX Error: ', status, error);
        }
    })
})


$('#weather tbody').on('click', 'tr.main-row', function() {
    const tr = $(this).closest('tr');
    const row = table.row(tr);
    const selectedRowId = tr.attr('id');

    if (row.child.isShown()) {
        row.child.hide();
        tr.removeClass('shown');
    } else {

        const index = findIndex(selectedRowId);
        const daysArr = database[index].days;

        row.child(format(daysArr, selectedRowId)).show();
        tr.addClass('shown')

        const nestedTable = new DataTable(`#days-${selectedRowId}`, {
            paging: false,
            searching: false,
            ordering: false,
            columns: [
                {data: 'date'},
                {data: 'mintemp_c'},
                {data: 'avgtemp_c'},
                {data: 'maxtemp_c'},
                {data: 'mintemp_f'},
                {data: 'avgtemp_f'},
                {data: 'maxtemp_f'},
                {data: 'daily_chance_of_snow'},
                {data: 'daily_chance_of_rain'},
                {data: 'avghumidity'},
                {data: 'sunrise'},
                {data: 'sunset'},
                {data: 'weatherStatus'}

            ],
            data: daysArr,
            columnDefs: [
                { targets: '_all', className: 'dt-center dt-body-center'}
            ],
            createdRow: function(row, data, dataIndex) {
                $(row).addClass('nested-row');
            },
            bInfo: false
        });
    }
});

function format(daysArr, id) {
    return `
        <table id="days-${id}" class="display nested-table">
            <thead class="thead-light">
                <tr>
                    <th>Date</th>
                    <th>Min C</th>
                    <th>Avg C</th>
                    <th>Max C</th>
                    <th>Min F</th>
                    <th>Avg F</th>
                    <th>Max F</th>
                    <th>% Snow</th>
                    <th>% Rain</th>
                    <th>Avg Humidity</th>
                    <th>Sunrise</th>
                    <th>Sunset</th>
                    <th>Forecast</th>
                </tr>
            </thead>
            <tbody>
                ${daysArr.map(day => `
                    <tr>
                    <td>${day.date}</td>
                    <td>${day.mintemp_c}</td>
                    <td>${day.avgtemp_c}</td>
                    <td>${day.maxtemp_c}</td>
                    <td>${day.mintemp_f}</td>
                    <td>${day.avgtemp_f}</td>
                    <td>${day.maxtemp_f}</td>
                    <td>${day.daily_chance_of_snow}</td>
                    <td>${day.daily_chance_of_rain}</td>
                    <td>${day.avghumidity}</td>
                    <td>${day.sunrise}</td>
                    <td>${day.sunset}</td>
                    <td>${day.weatherStatus}</td>              
                </tr>
             `).join('')}
        </tbody>
     </table>
     `;
}

function findIndex(id) {
    return database.findIndex((data) => {
        return data.id === Number(id);
    });
}
