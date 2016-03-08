/**
 * 定义超级类，其他类继承该类 定义小写的object基本类，用于实现最基础的方法等
 * @author xt
 */
var object =
{
		isA : function(aType) {//一个判断类与类之间及对象与类之间的关系的基础方法
			var self = this;
			while(self)
			{
				if (self == aType)
				{
					return true;
				}	
				self = self.Type;
			}
			return false;
		}
	,  isFunction	: function() {
			return Object.prototype.toString.call(obj) === '[object Function]';
		}
	,	isArray : function(obj) {   
			return Object.prototype.toString.call(obj) === '[object Array]';    
		}  
};
