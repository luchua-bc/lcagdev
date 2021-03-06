var lcag = lcag || {};

lcag.PaymentsGrid = lcag.PaymentsGrid || {
    grid: {},
    initialise: function() {
        $("#payments-grid").jqGrid({
            colModel: [
                { name: "id", label: "ID", hidden: false },
                { name: "userId", label: "Member", width: 300, template: "string", formatter: lcag.PaymentsGrid.formatters.userId },
                { name: "date", label: "Transaction Date", width: 100, template: "string", formatter: lcag.PaymentsGrid.formatters.date, search: false },
                { name: "transactionIndexOnDay", label: "Index", width: 90, template: "string" },
                { name: "description", label: "Description", width: 500, template: "string" },
                { name: "emailAddress", label: "Email Address", width: 200, template: "string" },
                { name: "amount", label: "Amount", width: 90, template: "string", formatter: lcag.PaymentsGrid.formatters.amount },
                { name: "runningBalance", label: "Running Balance", width: 120, template: "string", formatter: lcag.PaymentsGrid.formatters.runningBalance  },
                { name: "counterParty", label: "Counter Party", width: 150, template: "string" },
                { name: "reference", label: "Reference", width: 150, template: "string" },
                { name: "paymentSource", label: "Payment Source", width: 150, template: "string" },
                { name: "emailSent", label: "Email Sent", width: 59, formatter: lcag.PaymentsGrid.formatters.emailSent, stype: "select", searchoptions: { sopt: ["eq", "ne"], value: ":Any;1:Yes;0:No" } },
                { name: "excludeFromMemberReconciliation", label: "Exclude From Member Reconciliation", width: 150, formatter: lcag.PaymentsGrid.formatters.excludeFromMemberReconciliation, stype: "select", searchoptions: { sopt: ["eq", "ne"], value: ":Default (No);1:Yes;0:No" } },
                { name: "action", label: "", width: 110, formatter: lcag.MemberGrid.formatters.action, search: false }
            ],
            datatype: function(postData) {
                    jQuery.ajax({
                        url: lcag.Common.urlPrefix + '/payment',
                        data: postData,
                        dataType: "json",
                        complete: function(response, status) {
                            if (status == "success") {
                                lcag.PaymentsGrid.grid = $("#payments-grid");
                                lcag.PaymentsGrid.grid[0].addJSONData(response.responseJSON);
                            }
                        }
                    });
            },
            shrinkToFit: false,
            width: $(window).width() - 10,
            autoresizeOnLoad: true,

            iconSet: "fontAwesome",
            threeStateSort: false,
            headertitles: true,
            pager: true,
            rowNum: 25,
            altRows: true,
            viewrecords: true,
            gridComplete: function() {
                lcag.Statistics.refresh();
                $('.userIdSelect').select2({
                  width: "resolve",
                  ajax: {
                    url: '/member',
                    data: function (params) {
                      return { username: params.term, emailAddress: params.term, name: params.term, operator: 'or' }
                    },
                    dataType: 'json',
                    processResults: function (data) {
                        var arr = []
                        $.each(data.rows, function (index, row) {
                            arr.push({
                                id: row.id,
                                text: row.username + " (" + row.emailAddress + ") " + row.name
                            })
                        })
                        return {
                            results: arr
                        };
                    }
                  }
                }).on('select2:select', function (e) {
                    var memberId = $(this).val();
                    var paymentId = $(this).attr("id").split("_")[1];
                    $.ajax({
                      method: "POST",
                      url: lcag.Common.urlPrefix + "/payment/assignToMember",
                      data: { "memberId": memberId, "paymentId": paymentId }
                    }).done(function(result) {
                        lcag.Common.alertSuccess();
                        lcag.PaymentsGrid.grid.trigger("reloadGrid");
                        lcag.MemberGrid.grid.trigger("reloadGrid");
                    });
                });

                $("#payments-grid").find(".update-row-btn").on("click", function(e) {
                    var rowContext = this;
                    $.ajax({
                          type: "POST",
                          url: lcag.Common.urlPrefix + "/payment/update",
                          data: (function() {
                            var id = $(rowContext).data("row-id");

                            return {
                                "id": id,
                                "excludeFromMemberReconciliation": $("#excludeFromMemberReconciliation_" + id).prop("checked")
                            };
                          })(),
                          success: function(e) {
                            lcag.Common.alertSuccess();
                            lcag.PaymentsGrid.grid.trigger("reloadGrid");
                            lcag.MemberGrid.grid.trigger("reloadGrid");
                          },
                          error: function(e) {
                            lcag.Common.alertError();
                            lcag.PaymentsGrid.grid.trigger("reloadGrid");
                            lcag.MemberGrid.grid.trigger("reloadGrid");
                          }
                        });
                });
            }
        }).jqGrid("filterToolbar", {
            searchOnEnter: false
        });

        $(window).bind('resize', function() {
            $("#payments-grid").width($(window).width() -10);
            $("#payments-grid").setGridWidth($(window).width() -10);
            $("#payments-grid").setGridHeight($(window).height()-220);
        }).trigger('resize');

    },
	formatters: {
        "amount": function(cellvalue, options, row) {
            return '<div class="input-group"><div class="input-group"><div class="input-group-addon">£</div><input disabled="disabled" id="amount_' + row.id + '" type="text" value="' + row.amount + '" class="form-control"></div></div>';
        },
        "runningBalance": function(cellvalue, options, row) {
            return '<div class="input-group"><div class="input-group"><div class="input-group-addon">£</div><input disabled="disabled" id="amount_' + row.id + '" type="text" value="' + row.runningBalance + '" class="form-control"></div></div>';
        },
        "date": function(cellvalue, options, row) {
            return moment.unix(row.date).format("DD/MM/YYYY");
        },
        "userId": function(cellvalue, options, row) {
            if (row.userId == null) {
                return '<select style="width: 100%;" id="userId_' + row.id + '" class="userIdSelect"></select>';
            }
            return '<select style="width: 100%;" id="userId_' + row.id + '" class="userIdSelect"><option selected value="' + row.userId + '">' + row.username + ' (' + row.emailAddress + ')</option></select>';
        },
        "emailSent": function(cellvalue, options, row) {
            return '<input disabled="disabled" id="emailSent_' + row.id + '" type="checkbox" ' + (row.emailSent ? ' checked="checked"' : '') + '" data-row-id="' + row.id + '" />';
        },
        "excludeFromMemberReconciliation": function(cellvalue, options, row) {
            return '<input id="excludeFromMemberReconciliation_' + row.id + '" type="checkbox" ' + (row.excludeFromMemberReconciliation ? ' checked="checked"' : '') + '" data-row-id="' + row.id + '" />';
        },
        "action": function(cellvalue, options, row) {
            return '<button id="paymentUpdateButton_' + row.id + '" type="button" class="btn btn-default update-row-btn" data-row-id="' + row.id + '"><span class="fa fa-check fa-sm" aria-hidden="true"></span>&nbsp;Update</button>';
        }
    }
}


