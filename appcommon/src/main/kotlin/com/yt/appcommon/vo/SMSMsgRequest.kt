package com.yt.appcommon.vo

data class SMSMsgRequest(var payEndBankCode:String?,
                         var payMoney:String?,
                         var payTime:String?,
                         var payBankName:String?,
                         var bankRemainMoney:String?,
                         var msgContent:String?)