/**
 * 扩展easyui表单的验证
 * @author colorwolf
 * 
 */
$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
        validator: function (value) {
	            return /^[\u0391-\uFFE5]+$/.test(value);
	       },
        message: '只能输入汉字'
    },
   mobile: {
        validator: function (value) {
	            var reg = /^1[3|4|5|8|9]\d{9}$/;
	            return reg.test(value);
	       },
        message: '输入手机号码格式不准确.'
    },
   zipcode: {
        validator: function (value) {
	            var reg = /^[1-9]\d{5}$/;
	            return reg.test(value);
	       },
	     message: '邮编必须是非0开始的6位数字.'
    },
   account: {
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
                    return false;
                } else {
                    return true;
                }
            }
        }, message: ''
    },
    /**
     * <input validType="compare['#other','${type }']" >
     * type 默认等于(eq): lt : 小于，gt：大于，eq：等于
     */
   compare:{
	   validator: function (value, param) {
		   var otherVal = $(param[0]).val();
		   var type = param[1];
		   if (!param[1]) {
			   type == 'eq';
		    }
		   // console.log(type,value, otherVal);
		   if (otherVal != '' && value != '') {
			   if (type == 'lt'){
				   return value < otherVal;
			   } else if (type == 'gt') {
				   return value > otherVal;
			   } else {
				   return value == otherVal;
			   }
		    }
		   return true;
	    },
	    message:'输入项值不匹配'
   	}
   	
});