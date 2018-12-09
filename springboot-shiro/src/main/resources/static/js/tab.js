(function($) {
	
	/**
	 * 初始化tab选项卡、面板样式
	 * @param {Object} $tab
	 */
	function initTab($tab) {
		$tab.addClass("tab")
			.children("ul").addClass("tab-header")
			.append($("<li></li>").addClass("tab-header-items").css("width", $tab.outerWidth() - 25).append("<ul></ul>"))
			.append($("<li></li>").addClass("tab-header-select tab-header-select-down").css("title", "显示隐藏").append("<ul class='hide-title-list'></ul>"));
		$tab.append($("<ul></ul>").addClass("tab-overflow-items")).children("div").addClass("tab-content");
	}

	/**
	 * 初始化tab总高度、内容面板高度
	 * @param {Object} $tab
	 */
	function initContentHeight($tab) {
		$tab.css("height", ($tab.parent().height() - 2) + "px")
			.find(".tab-content").css("height", ($tab.parent().height() - 58) + "px");
	}
	
	/**
	 * 初始化选项卡点击事件、关闭按钮点击事件
	 * @param {Object} $tab
	 */
	function initEvents($tab) {
		$tab
		// 选项卡点击事件
		.delegate(".tab-header-item", "click", function(){
			var selected = $(this).hasClass("tab-header-selected");
			if (!selected) {
				// 如果这个选项卡没有选中，就调用selectTab函数进行选中
				selectTab($tab, $(this).attr("target"));
			}
		})
		// 打开隐藏标签选择框
		.delegate(".tab-header-select", "click", function(){
			if($(this).hasClass("tab-header-select-down") && $(this).children().children().length > 0) {
				$(this).removeClass("tab-header-select-down").addClass("tab-header-select-up");
				$(this).children().css("display", "block");
			} else {
				$(this).removeClass("tab-header-select-up").addClass("tab-header-select-down");
				$(this).children().css("display", "none");
			}
		})
		// 打开隐藏标签选择框
		.delegate(".tab-header-select ul li", "click", function(){
			// 获取选中的隐藏标签的item-id
			var tabId = $(this).attr("item-id");
			// 选中这个标签
			selectTab($tab, tabId);
		})
		// 关闭按钮点击事件
		.delegate(".close", "click", function() {
			// 获取需要关闭的tab标签的tabId
			var tabId = $(this).parent().attr("target");
			// 调用removeTab函数关闭指定标签
			removeTab($tab, tabId);
		});
	}
	
	/**
	 * 初始化标签右键菜单
	 */
	function initContextMenu() {
		var contextMenu = $("body .tab-contextmenu");
		if (!contextMenu[0]) {
			$("<div class='tab-contextmenu'></div>")
				.append(createContextMenuItem("关闭当前标签", "current"))
				.append(createContextMenuItem("关闭左侧标签", "prevAll"))
				.append(createContextMenuItem("关闭右侧标签", "nextAll"))
				.append(createContextMenuItem("关闭其他", "other"))
				.append(createContextMenuItem("关闭全部", "all"))
				.appendTo("body");
		}
		// 创建一个右键菜单项
		function createContextMenuItem(text, target) {
			return $("<div class='tab-contextmenu-item'></div>").html(text).attr("target", target);
		}
	}
	
	/**
	 * 初始化tab标签的右键菜单功能
	 * http://www.cnblogs.com/splitgroup/p/6921069.html
	 * JS简单实现自定义右键菜单
	 */
	function initWindowContextMenu() {
		// 给body绑定两个事件
		$("body")
		// 右键菜单显示
		.bind("contextmenu", contextMenuHandler)
		// 关闭右键菜单
		.on("click", function(ev) {
			// 关闭右键菜单
			$(".tab-contextmenu").css("display", "none");
			
			// 关闭隐藏标签选择框
			// 获取事件对象，需要兼容IE
			var e = ev || window.event;
			var src = $(e.srcElement || e.target);
			if(!src.hasClass("tab-header-select"))
				closeSelect();
		});
	}
	
	/**
	 * body元素的contextmenu事件执行函数
	 * @param {Object} ev
	 */
	function contextMenuHandler(ev) {
		
		// 关闭隐藏标签选择框
		closeSelect();
		
		// 获取事件对象，需要兼容IE
		var e = ev || window.event;
		
		// 获取自定义的右键菜单
		var menu = $(".tab-contextmenu");
		
		// 获取事件源
		// e.srcElement，兼容IE、360、chrome
		// e.target，兼容Firefox
		src = $(e.srcElement || e.target);
		
		// 如果事件源对象是tab标签才显示右键菜单、绑定事件
		if (src.hasClass("tab-header-item")) {
			// 获取tab组件
			var tab = src.parentsUntil(".tab").parent();
			// 选中点击的标签
			tab.tab("selectTab", src.attr("target"));
			// 取消默认的浏览器右键菜单
			e.preventDefault();
			// 根据事件对象中鼠标点击的位置，进行定位
			// 之后根据点击的标签进行事件绑定
			menu
				.css({"left": e.clientX + 'px', "top": e.clientY + 'px', "display": "block"})
				.children().unbind("click").bind("click", function() {
					// 判断关闭类型：关闭当前标签、关闭左侧标签、关闭右侧标签、关闭其他、关闭全部
					switch($(this).attr("target")){
						case 'current':
							return removeTabs(tab, src);
						case 'prevAll':
							return removeTabs(tab, src.prevAll());
						case 'nextAll':
							removeHideTitles(tab);
							return removeTabs(tab, src.nextAll());
						case 'other':
							removeHideTitles(tab);
							return removeTabs(tab, src.siblings());
						case 'all':
							removeHideTitles(tab);
							return removeTabs(tab, src.parent().children());
					}
				});
		} else {
			menu.css("display", "none");
		}
	}
	
	/**
	 * 添加tab选项卡
	 * @param {Object} $tab
	 * @param {Object} param {"title": "", "id": "", "content": ""}
	 */
	function addTab($tab, param) {
		
		if (isExists($tab, param["id"])) {
			// 如果选项卡已经存在，则调用selectTab函数将其选中
			selectTab($tab, param["id"]);
		} else{
			// 如果选项卡不存在，则先创建再将其选中

			// 去掉选项卡标签的选中样式
			$tab.find(".tab-header-item").removeClass("tab-header-selected");
			// 创建选项卡标签
			$newHeaderItem = $("<li></li>");
			$newHeaderItem
				.text(param["title"])
				.attr("target", param["id"])
				.addClass("tab-header-item").addClass("tab-header-selected")
				.append($("<span class=\"close\"></span>")).appendTo($tab.find(".tab-header-items").children("ul"));

			// 隐藏所有选项卡面板
			$tab.children("div").children().addClass("hide");
			// 创建新的选项卡面板
			$newContentItem = $("<div></div>");
			$newContentItem
				.html(param["content"])
				.attr("id", param["id"])
				.addClass("tab-content-item")
				.appendTo($tab.children("div"));
			
			afterAddTab($tab);
		}
	}
	
	/**
	 * 在标签header中添加了新的标签时执行
	 * @param {Object} $tab
	 */
	function afterAddTab($tab) {
		// 获取可见标签容器
		var headItem = $tab.find(".tab-header-items");
		
		// 获取全部课件标签
		var titles = headItem.find("ul").children();
		
		// 当标签个数小于等于1个时，不做任何操作
		if(titles.length <= 1) return;
		
		// 获取第一个可见标签的左偏移量
		var w1 = titles.eq(0).offset().left;
		// 获取最后一个可见标签的左偏移量
		var w2 = titles.eq(titles.length - 1).offset().left;
		
		// 当最后一个标签在第一个右边时，说明可见标签个数未超出父元素宽度，不做任何操作
		if(w2 > w1) return;
		
		// 获取可见标签容器的宽度
		var headerWidth = headItem.outerWidth();
		// 获取最后一个可见标签的宽度
		var tmp = titles.eq(titles.length - 1).outerWidth(true);
		var maxVisible = 0;
		
		titles.each(function(i) {
			var w = $(this).outerWidth(true);
			tmp += w;
			if(tmp > headerWidth) {
				maxVisible = i;
				return false;
			}
		});
		
		var select = $tab.find(".tab-header-select ul");
		// 把需要隐藏的标签隐藏起来
		for(var j = maxVisible; j < titles.length - 1; j++) {
			var t = titles.eq(j);
			t.removeClass("tab-header-selected")
				.attr("real-width",  t.outerWidth())
				.appendTo($tab.find(".tab-overflow-items"));
			select.append("<li item-id='" + t.attr("target") + "'>" + t.text() + "</li>");
		}
	}
	
	/**
	 * 关闭隐藏标签选择框
	 */
	function closeSelect() {
		if($(".tab-header-select").hasClass("tab-header-select-up")) {
			$(".tab-header-select")
				.removeClass("tab-header-select-up").addClass("tab-header-select-down")
				.children().css("display", "none");
		}
	}
	
	/**
	 * 删除全部隐藏标签
	 * @param {Object} $tab
	 */
	function removeHideTitles($tab) {
		$tab.find(".tab-overflow-items").children().remove();
		$tab.find(".hide-title-list").children().remove();
	}
	
	/**
	 * 添加tab选项卡和面板，再从远程url获取数据填充到面板中
	 * @param {Object} $tab
	 * @param {Object} param {"title": "", "id": "", "url": "", "method": ""}
	 */
	function addRemoteTab($tab, param) {
		// 添加选项卡
		addTab($tab, {"title": param["title"], "id": param["id"], "content": ""});
		// 获取数据，然后填充到新添加的面板
		$.ajax({
			type: param["method"] || "post",
			dataType: "html",
			url: param["url"],
			cache: false,
			success: function(data) {
				// 填充数据到面板
				$tab.find("#" + param["id"]).html(data);
			}
		});
	}
	
	/**
	 * 删除tab选项卡
	 * @param {Object} $tab
	 * @param {Object} tabId
	 */
	function removeTab($tab, tabId) {
		// 获取待删除选项卡标签
		var headerItem = $tab.find(".tab-header-items ul").children("li[target="+ tabId +"]");
		// 获取该选项卡是否被选中
		var selected = headerItem.hasClass("tab-header-selected");
		// 获取前一个选项卡
		var prevItem = headerItem.prev();
		// 该标签的前一个或者后一个标签id
		var nextSelectedId = null;
		
		// 如果没有前一个，则获取后一个
		if (!prevItem[0])
			prevItem = headerItem.next();
		
		if (prevItem[0]) {
			nextSelectedId = prevItem.attr("target");
		} else {
			var hide1 = $tab.find(".hide-title-list li").eq(0);
			if (hide1[0]) nextSelectedId = hide1.attr("item-id");
		}
		
		// 删除选项卡标签
		headerItem.remove();
		// 删除选项卡面板
		$tab.children("div").children("#" + tabId).remove();
		
		// 把隐藏的面板移上来
		var head = $tab.children("ul");
		var titles = head.find(".tab-header-items ul").children();
		
		var hideTitles = $tab.find(".tab-overflow-items").children();
		
		var visibleWidth = 0;
		
		titles.each(function() {
			visibleWidth += $(this).outerWidth();
		});
		
		var j;
		var headerWidth = head.children(".tab-header-items").outerWidth();
		for(j = 0; j < hideTitles.length; j++) {
			visibleWidth += parseInt(hideTitles.eq(j).attr("real-width"));
			if (visibleWidth >= headerWidth)
				break;
		}
		
		for(var k = 0; k < j; k++) {
			hideTitles.eq(k).appendTo($tab.find(".tab-header-items").children("ul"));
			$tab.find(".hide-title-list").children().eq(k).remove();
		}
		
		// 如果待删除选项卡已经被选中且有相邻标签，则将相邻选项卡选中
		if (selected && nextSelectedId)
			selectTab($tab, nextSelectedId);
	}
	
	/**
	 * 批量删除tab选项卡
	 * @param {Object} $tab
	 * @param {Object} items
	 */
	function removeTabs($tab, items) {
		// 遍历需要关闭的标签对象，逐一进行关闭
		items.each(function() {
			// 调用removeTab函数关闭一个需要关闭的标签
			removeTab($tab, $(this).attr("target"));
		});
	}
	
	/**
	 * 选中指定tab标签
	 * @param {Object} $tab
	 * @param {Object} tabId
	 */
	function selectTab($tab, tabId) {
		
		// 调整选项卡标签样式
		
		var headItems = $tab.find(".tab-header-items");
		var tab = headItems.find("li[target=" + tabId + "]");
		
		if(tab[0]) {
			tab.addClass("tab-header-selected")
				.siblings().removeClass("tab-header-selected");
		} else {
			tab = $tab.find(".tab-overflow-items").children("li[target=" + tabId + "]");
			if(tab[0]) {
				headItems.find("ul li").removeClass("tab-header-selected");
				tab.addClass("tab-header-selected").appendTo(headItems.children("ul"));
				$tab.find(".hide-title-list").children("li[item-id=" + tabId + "]").remove();
			}
		}
		
		// 调整选项卡面板样式
		$tab
			.find("#" + tabId).removeClass("hide")
			.siblings().addClass("hide");
		
		afterAddTab($tab);
	}
	
	/**
	 * 判断是否选中指定tab标签
	 * @param {Object} $tab
	 * @param {Object} tabId
	 */
	function isSelected($tab, tabId) {
		return $tab.find("li[target=" + tabId + "]").hasClass("tab-header-selected");
	}
	
	/**
	 * 判断指定tab标签是否存在
	 * @param {Object} $tab
	 * @param {Object} tabId
	 */
	function isExists($tab, tabId) {
		return $tab.find("li[target=" + tabId + "]")[0] != undefined;
	}

	$.fn.tab = function(options, param) {
		
		if (typeof options == 'string') {
			switch(options){
				case 'addTab':
					return this.each(function() {
						addTab($(this), param);
					});
				case 'addRemoteTab':
					return this.each(function() {
						addRemoteTab($(this), param);
					});
				case 'removeTab':
					return this.each(function() {
						removeTab($(this), param);
					});
				case 'selectTab':
					return this.each(function() {
						selectTab($(this), param);
					});
				case 'isSelected':
					return isSelected($(this), param);
				case 'isExists':
					return isExists($(this), param);
			}
		}
		
		options = options || {};

		return this.each(function() {
			// 保存对象
			var tab = $(this);
			// 初始化tab选项卡、面板样式
			initTab(tab);
			// 初始化标签的右键菜单和菜单项
			initContextMenu();
			// 初始化tab总高度、内容面板高度
			initContentHeight(tab);
			// 初始化选项卡点击事件、关闭按钮点击事件
			initEvents(tab);
			// 给body绑定事件，用于显示、关闭tab标签右键菜单
			initWindowContextMenu();
		});
	};
})(jQuery);