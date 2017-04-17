/**
 * Created by MariLiis on 15.04.2017.
 */

$(document).ready(function () {
    $('#visitBeginningDateTime').datetimepicker({
        format:'d.m.Y H:i',
        i18n:{
            et:{
                months:[
                    'Jaanuar','Veebruar','Märts','Aprill',
                    'Mai','Juuni','Juuli','August',
                    'September','Oktoober','November','Detsember'
                ],
                dayOfWeek:[
                    "P", "E", "T", "K", "N", "R", "L"
                ]
            }
        }
    });

    $('#visitEndDateTime').datetimepicker({
        format:'d.m.Y H:i',
        i18n:{
            et:{
                months:[
                    'Jaanuar','Veebruar','Märts','Aprill',
                    'Mai','Juuni','Juuli','August',
                    'September','Oktoober','November','Detsember'
                ],
                dayOfWeek:[
                    "P", "E", "T", "K", "N", "R", "L"
                ]
            }
        }
    });
    $.datetimepicker.setLocale('et');

});
