class Api {

    static post(url, data, success, error) {
        $.post({
            url: url,
            data: data,
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    success(data)
                } else {
                    error(data)
                }
            },
            error: function (data) {
                error(data)
            }
        });
    }

    static get(url, data, success, error) {
        $.ajax({
            url: url,
            data: data,
            type: "Get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    success(data)
                } else {
                    error(data)
                }
            },
            error: function (data) {
                error(data)
            }
        });
    }
}

post = function post(url, data, success, error) {
    $.post({
        url: url,
        data: data,
        dataType: "json",
        contentType: 'application/json',
        success: function (data) {
            if (data.success) {
                success(data)
            } else {
                error(data)
            }
        },
        error: function (data) {
            error(data)
        }
    });
};