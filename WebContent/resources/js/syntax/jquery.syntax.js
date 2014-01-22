﻿$(function(){
//用js实现代码高亮现实
//Download by http://www.codefans.net
//最后修改:it农民
//最后修改时间:2008年12月11日
var dp={
	sh:{
		Toolbar:{},
		Utils:{},
		RegexLib:{},
		Brushes:{},
		Strings:{},
		Version:"1.4.1"
	}
};

$.extend({dp:dp});

dp.SyntaxHighlighter=dp.sh;
dp.sh.Toolbar.Commands={
	//扩展代码
	ExpandSource:{
		label:"+ expand source",
		check:function(_1){
			return _1.collapse;
		},
		func:function(_2,_3){
			_2.parentNode.removeChild(_2);
			_3.div.className=_3.div.className.replace("collapsed","");
		}
	},
	//查看源代码
	ViewSource:{
		label:"view plain",
		func:function(_4,_5){
			var _6=_5.originalCode.replace(/</g,"&lt;");
			var _7=window.open("","_blank","width=750, height=400, location=0, resizable=1, menubar=0, scrollbars=1");
			_7.document.write("<textarea style=\"width:99%;height:99%\">"+_6+"</textarea>");
			_7.document.close();
		}
	},
	//复制到剪贴板
	CopyToClipboard:{
		label:"copy to clipboard",
		check:function(){
			return window.clipboardData!=null;
		},
		func:function(_8,_9){
			window.clipboardData.setData("text",_9.originalCode);
			alert("The code is in your clipboard now");
		}
	},
	//打印代码
	PrintSource:{
		label:"print",
		func:function(_a,_b){
			var _c=document.createElement("IFRAME");//创建一个IFRAME标签
			var _d=null;
			_c.style.cssText="position:absolute;width:0px;height:0px;left:-500px;top:-500px;";//设置CSS
			document.body.appendChild(_c);//加载该子节点
			_d=_c.contentWindow.document; //获得该节点的内容
			dp.sh.Utils.CopyStyles(_d,window.document);
			_d.write("<div class=\""+_b.div.className.replace("collapsed","")+" printing\">"+_b.div.innerHTML+"</div>");
			_d.close();
			_c.contentWindow.focus();
			_c.contentWindow.print();
			alert("Printing...");
			document.body.removeChild(_c);
		}
	},
	About:{
		label:"?",
		func:function(_e){
			var _f=window.open("","_blank","dialog,width=300,height=150,scrollbars=0");
			var doc=_f.document;
			dp.sh.Utils.CopyStyles(doc,window.document);
			doc.write(dp.sh.Strings.AboutDialog.replace("{V}",dp.sh.Version));
			doc.close();
			_f.focus();
		}
	}
};
dp.sh.Toolbar.Create=function(_11){
	var div=document.createElement("DIV");
	div.className="tools";
	for(var _13 in dp.sh.Toolbar.Commands){
		var cmd=dp.sh.Toolbar.Commands[_13];
		if(cmd.check!=null&&!cmd.check(_11)){
			continue;
		}
		div.innerHTML+="<a href=\"#\" onclick=\"$.dp.sh.Toolbar.Command('"+_13+"',this);return false;\">"+cmd.label+"</a>";
	}
	return div;
};
dp.sh.Toolbar.Command=function(_15,_16){
	var n=_16;
	while(n!=null&&n.className.indexOf("dp-highlighter")==-1){
		n=n.parentNode;
	}
	if(n!=null){
		dp.sh.Toolbar.Commands[_15].func(_16,n.highlighter);
	}
};
dp.sh.Utils.CopyStyles=function(_18,_19){
	var _1a=_19.getElementsByTagName("link");
	for(var i=0;i<_1a.length;i++){
		if(_1a[i].rel.toLowerCase()=="stylesheet"){
			_18.write("<link type=\"text/css\" rel=\"stylesheet\" href=\""+_1a[i].href+"\"></link>");
		}
	}
};
dp.sh.RegexLib={
	MultiLineCComments:new RegExp("/\\*[\\s\\S]*?\\*/","gm"),
	SingleLineCComments:new RegExp("//.*$","gm"),
	SingleLinePerlComments:new RegExp("#.*$","gm"),
	DoubleQuotedString:new RegExp("\"(?:\\.|(\\\\\\\")|[^\\\"\"])*\"","g"),
	SingleQuotedString:new RegExp("'(?:\\.|(\\\\\\')|[^\\''])*'","g")
};
dp.sh.Match=function(_1c,_1d,css){
	this.value=_1c;
	this.index=_1d;
	this.length=_1c.length;
	this.css=css;
};
dp.sh.Highlighter=function(){
	this.noGutter=false;
	this.addControls=true;
	this.collapse=false;
	this.tabsToSpaces=true;
	this.wrapColumn=80;
	this.showColumns=true;
};
dp.sh.Highlighter.SortCallback=function(m1,m2){
	if(m1.index<m2.index){
		return -1;
	}
	else{
		if(m1.index>m2.index){
			return 1;
		}
		else{
		  if(m1.length<m2.length){
		  	return -1;
		  }
		  else{
		  	if(m1.length>m2.length){
		  		return 1;
		  	}
		  }
		 }
		}
		return 0;
};
dp.sh.Highlighter.prototype.CreateElement=function(_21){
	var _22=document.createElement(_21);
	_22.highlighter=this;
	return _22;
};
dp.sh.Highlighter.prototype.GetMatches=function(_23,css){
	var _25=0;
	var _26=null;
	while((_26=_23.exec(this.code))!=null){
		this.matches[this.matches.length]=new dp.sh.Match(_26[0],_26.index,css);
	}
};
dp.sh.Highlighter.prototype.AddBit=function(str,css){
	if(str==null||str.length==0){
		return;
	}
	var _29=this.CreateElement("SPAN");
	str=str.replace(/&/g,"&amp;");
	str=str.replace(/ /g,"&nbsp;");
	str=str.replace(/</g,"&lt;");
	str=str.replace(/\n/gm,"&nbsp;<br>");
	if(css!=null){
		var _2a=new RegExp("<br>","gi");
		if(_2a.test(str)){
			var _2b=str.split("&nbsp;<br>");
			str="";
			for(var i=0;i<_2b.length;i++){
				_29=this.CreateElement("SPAN");
				_29.className=css;
				_29.innerHTML=_2b[i];
				this.div.appendChild(_29);
				if(i+1<_2b.length){
					this.div.appendChild(this.CreateElement("BR"));
				}
			}
		}
		else{
			_29.className=css;
			_29.innerHTML=str;
			this.div.appendChild(_29);
		}
	}
	else{
		_29.innerHTML=str;
		this.div.appendChild(_29);
	}
};
dp.sh.Highlighter.prototype.IsInside=function(_2d){
	if(_2d==null||_2d.length==0){
		return false;
	}
	for(var i=0;i<this.matches.length;i++){
		var c=this.matches[i];
		if(c==null){continue;}
		if((_2d.index>c.index)&&(_2d.index<c.index+c.length)){return true;}
	}
	return false;
};
dp.sh.Highlighter.prototype.ProcessRegexList=function(){
	for(var i=0;i<this.regexList.length;i++){
		this.GetMatches(this.regexList[i].regex,this.regexList[i].css);
	}
};
dp.sh.Highlighter.prototype.ProcessSmartTabs=function(_31){
	var _32=_31.split("\n");
	var _33="";
	var _34=4;
	var tab="\t";
	function InsertSpaces(_36,pos,_38){
		var _39=_36.substr(0,pos);
		var _3a=_36.substr(pos+1,_36.length);
		var _3b="";
		for(var i=0;i<_38;i++){
			_3b+=" ";
		}
		return _39+_3b+_3a;
	}
	function ProcessLine(_3d,_3e){
		if(_3d.indexOf(tab)==-1){return _3d;}
		var pos=0;
		while((pos=_3d.indexOf(tab))!=-1){
			var _40=_3e-pos%_3e;
			_3d=InsertSpaces(_3d,pos,_40);
		}
		return _3d;
	}
	for(var i=0;i<_32.length;i++){
		_33+=ProcessLine(_32[i],_34)+"\n";
	}
	return _33;
};
dp.sh.Highlighter.prototype.SwitchToList=function(){
	var _42=this.div.innerHTML.replace(/<(br)\/?>/gi,"\n");
	var _43=_42.split("\n");
	if(this.addControls==true){
		this.bar.appendChild(dp.sh.Toolbar.Create(this));
	}
	if(this.showColumns){
		var div=this.CreateElement("div");
		var _45=this.CreateElement("div");
		var _46=10;
		var i=1;while(i<=150){
			if(i%_46==0){
				div.innerHTML+=i;i+=(i+"").length;
			}
			else{
				div.innerHTML+="&middot;";
				i++;
			}
		}
		_45.className="columns";
		_45.appendChild(div);
		this.bar.appendChild(_45);
	}
	for(var i=0,lineIndex=this.firstLine;i<_43.length-1;i++,lineIndex++){
		var li=this.CreateElement("LI");
		var _4a=this.CreateElement("SPAN");
		li.className=(i%2==0)?"alt":"";
		_4a.innerHTML=_43[i]+"&nbsp;";
		li.appendChild(_4a);
		this.ol.appendChild(li);
	}
	this.div.innerHTML="";
};
dp.sh.Highlighter.prototype.Highlight=function(_4b){
	function Trim(str){
		return str.replace(/^\s*(.*?)[\s\n]*$/g,"$1");
	}
	function Chop(str){
		return str.replace(/\n*$/,"").replace(/^\n*/,"");
	}
	function Unindent(str){
		var _4f=str.split("\n");
		var _50=new Array();
		var _51=new RegExp("^\\s*","g");
		var min=1000;
		for(var i=0;i<_4f.length&&min>0;i++){
			if(Trim(_4f[i]).length==0){continue;}
			var _54=_51.exec(_4f[i]);
			if(_54!=null&&_54.length>0){min=Math.min(_54[0].length,min);}
		}
		if(min>0){
			for(var i=0;i<_4f.length;i++){
				_4f[i]=_4f[i].substr(min);
			}
		}
		return _4f.join("\n");
	}
	function Copy(_56,_57,_58){
		return _56.substr(_57,_58-_57);
	}
	var pos=0;
	this.originalCode=_4b;
	this.code=Chop(Unindent(_4b));
	this.div=this.CreateElement("DIV");
	this.bar=this.CreateElement("DIV");
	this.ol=this.CreateElement("OL");
	this.matches=new Array();
	this.div.className="dp-highlighter";
	this.div.highlighter=this;
	this.bar.className="bar";
	this.ol.start=this.firstLine;
	if(this.CssClass!=null){
		this.ol.className=this.CssClass;
	}
	if(this.collapse){
		this.div.className+=" collapsed";
	}
	if(this.noGutter){
		this.div.className+=" nogutter";
	}
	if(this.tabsToSpaces==true){
		this.code=this.ProcessSmartTabs(this.code);
	}
	this.ProcessRegexList();
	if(this.matches.length==0){
		this.AddBit(this.code,null);
		this.SwitchToList();
		this.div.appendChild(this.ol);
		return;
	}
	this.matches=this.matches.sort(dp.sh.Highlighter.SortCallback);
	for(var i=0;i<this.matches.length;i++){
		if(this.IsInside(this.matches[i])){
			this.matches[i]=null;
		}
	}
	for(var i=0;i<this.matches.length;i++){
		var _5c=this.matches[i];
		if(_5c==null||_5c.length==0){continue;}
		this.AddBit(Copy(this.code,pos,_5c.index),null);
		this.AddBit(_5c.value,_5c.css);pos=_5c.index+_5c.length;
	}
	this.AddBit(this.code.substr(pos),null);
	this.SwitchToList();
	this.div.appendChild(this.bar);
	this.div.appendChild(this.ol);
};
dp.sh.Highlighter.prototype.GetKeywords=function(str){
	return "\\b"+str.replace(/ /g,"\\b|\\b")+"\\b";
};
dp.sh.HighlightAll=function(_5e,_5f,_60,_61,_62,_63){
	function FindValue(){
		var a=arguments;
		for(var i=0;i<a.length;i++){
			if(a[i]==null){continue;}
			if(typeof (a[i])=="string"&&a[i]!=""){
				return a[i]+"";
			}
			if(typeof (a[i])=="object"&&a[i].value!=""){return a[i].value+"";}
		}
		return null;
	}
	function IsOptionSet(_66,_67){
		for(var i=0;i<_67.length;i++){
			if(_67[i]==_66){return true;}
		}
	  return false;
	}
	function GetOptionValue(_69,_6a,_6b){
		var _6c=new RegExp("^"+_69+"\\[(\\w+)\\]$","gi");
		var _6d=null;
		for(var i=0;i<_6a.length;i++){
			if((_6d=_6c.exec(_6a[i]))!=null){
				return _6d[1];
			}
		}
		return _6b;
	}
	var _6f=document.getElementsByName(_5e);
	var _70=null;var _71=new Object();
	var _72="value";
	if(_6f==null){return;}
	for(var _73 in dp.sh.Brushes){
		var _74=dp.sh.Brushes[_73].Aliases;
		if(_74==null){continue;}
		for(var i=0;i<_74.length;i++){
			_71[_74[i]]=_73;
		}
	}
	for(var i=0;i<_6f.length;i++){
		var _77=_6f[i];
		var _78=FindValue(_77.attributes["class"],_77.className,_77.attributes["language"],_77.language);
		var _79="";
		if(_78==null){continue;}
		_78=_78.split(":");
		_79=_78[0].toLowerCase();
		if(_71[_79]==null){continue;}
		_70=new dp.sh.Brushes[_71[_79]]();
		_77.style.display="none";
		_70.noGutter=(_5f==null)?IsOptionSet("nogutter",_78):!_5f;
		_70.addControls=(_60==null)?!IsOptionSet("nocontrols",_78):_60;
		_70.collapse=(_61==null)?IsOptionSet("collapse",_78):_61;
		_70.showColumns=(_63==null)?IsOptionSet("showcolumns",_78):_63;
		_70.firstLine=(_62==null)?parseInt(GetOptionValue("firstline",_78,1)):_62;
		_70.Highlight(_77[_72]);
		_77.parentNode.insertBefore(_70.div,_77);
	}
};
$.fn.syntax=function(opt){
	var cfg={
		type:'Php'
	};
	$.extend(cfg,opt);
	var name=$(this).attr('name');
	$(this).attr('class',cfg.type);
	var fileName='shBrush'+cfg.type+'.js';
	$.get('./Scripts/'+fileName,function(data){
		eval(data);
		dp.SyntaxHighlighter.HighlightAll(name);
	});
}
});