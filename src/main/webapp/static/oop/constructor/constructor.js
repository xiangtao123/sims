/**
 * 提供面向对象编程接口 类及对象的创建及声明规则
 * @param aBaseClass
 * @param aClassDefine
 * @returns {class_}
 * @author xt
 */
function Class(aBaseClass,aClassDefine)//创建类的函数，用于声明类及继承关系
{
	function class_()//创建的临时函数壳
	{
		this.Type = aBaseClass; //我们约定给每一个类约定一个Type属性，引用其继承的类
		for( var member in aClassDefine )
		{
			this[member] = aClassDefine[member];//复制类的全部定义到当前创建的类
		}
	};
	class_.prototype = aBaseClass;
	return new class_();
};


/**
 * 创建对象的函数，用于任意类的对象创建
 * @param aClass
 * @param aParams
 * @returns {new_}
 */
function New(aClass,aParams)
{
	function new_()//创建对象的临时函数壳
	{
		this.Type = aClass;
		if(aClass.Create)
		{
			aClass.Create.apply(this,aParams);//我们约定所有类的构造函数叫Create
		}
	};
	new_.prototype = aClass;
	return new new_();
};
