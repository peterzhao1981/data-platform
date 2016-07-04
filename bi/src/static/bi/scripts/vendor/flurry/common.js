

window.Flurry = window.Flurry || {};
Flurry.analyticsApiKey = "KHYNC4DTWF2MJX6MWWHK";
/*
	creates a jquery object by either looking something up by id
	or passing back the jquery obj passed
*/
$.extend({
	maker : function(idOrObj){
		var o = idOrObj || null;
		
		if (o == null){
			return $(o);
		}else if (typeof(o) === 'string' || o instanceof String){
			//string so we treat as id
			return $("#" + o);
		}else if (o instanceof jQuery) {
			//jquery object already
			return o;
		}else{
			//html element, some other kind of object
			return $(o);
		}
	}
});

function hideLastMenu(e, id){};

//idStr = html id string for clicker element
//menuID = id for the menu div that appears
function globalMenuHandleClick(e, idStr, menuID) {
	var menu = $.maker(menuID);
	var clicked = $.maker(idStr);
	
	// hide the drop-down if we click anywhere else
	
	// have to stop this event so that it doesn't bubble
	// up to body and immediately hide itself.
	e.stopPropagation();
	e.preventDefault();
	
	//also must supports multiple menus on the same page
	//with the global hideLastMenu
	
	//call the saved menuClose function from last opened menu
	hideLastMenu(e, idStr);
	//create the new one
	hideLastMenu = function(newE, id) 
	{
		var newClicked = $.maker(id);
		var newTarget = $.maker(newE.target);

		if (	!clicked.is(newClicked)
				&& !newClicked.is(menu)
				&& (
					newTarget.length == 0 || 
					!newTarget.is(menu)
				)
				&& newTarget.parents().index(menu) < 0
		){ 
			//hide this menu if we clicked outside of the menu
			menu.hide(); 
		}else{
			//replace the hideLastMenu that was just triggered 
			//because the menu is part of the body too
			$('body').one('click', function(bodyE) {
				hideLastMenu(bodyE, $('body'));
			});
		}
	};
	//attach
	$('body')
		.off('click.globalMenuHide')
		.one('click.globalMenuHide', function(bodyE) {
			hideLastMenu(bodyE, $('body'));
		})
	;

	//hidden/visibleMenu used in case there is conflict with other menus on page
	//hideLastMenu won't know about these classes so this will ensure correct visible state for menu
	if (menu.hasClass("hiddenMenu")){
		menu
			.removeClass("hiddenMenu")
			.hide()
		;
	} else if (menu.hasClass("visibleMenu")){
		menu
			.removeClass("visibleMenu")
			.show()
		;
	}else{
		//toggle menu
		menu.toggle();
	}
}
function dropDownArrowStateInit($arrow)
{
	//arrow mouseovers - populate the mouseovers
	var removeArrowState = function($arrowDiv)	//remove class-based arrow states
	{
		$arrowDiv.removeClass("arrowDiv")
			 .removeClass("arrowDownDiv")
			 .removeClass("arrowOverDiv");
		$arrowDiv.find("img").attr("src", "/images/uiRefresh/icons/contentBarDownArrow_black.png");
	};
	//init
	removeArrowState($arrow);
	$arrow.addClass("arrowDiv");
	$arrow.hover(function() {
		removeArrowState($(this));
		$(this).addClass("arrowOverDiv");
		$(this).find("img").attr("src", "/images/uiRefresh/icons/contentBarDownArrow_black.png");
	}, function() {
		removeArrowState($(this));
		$(this).addClass("arrowDiv");
		$(this).find("img").attr("src", "/images/uiRefresh/icons/contentBarDownArrow_black.png");
	});
	$arrow.mousedown(function() {
		removeArrowState($(this));
		$(this).addClass("arrowDownDiv");
		$(this).find("img").attr("src", "/images/uiRefresh/icons/contentBarDownArrow_black.png");
	});
	$arrow.mouseup(function() {
		removeArrowState($(this));
		$(this).addClass("arrowDiv");
		$(this).find("img").attr("src", "/images/uiRefresh/icons/contentBarDownArrow_black.png");
	});
}
function createDropDown(id, config)
{
	//global click handler - allows you to click off the menu anywhere have hide the menu
	var activeTabID = "dropDownActiveTab_" + id;	//top row thats always displayed with arrow
	$("#" + id + " .dropDownActiveTab").attr("id", activeTabID);
	var dropDownID = "dropDownDiv_" + id;			//submenu thats sometimes hidden
	$("#" + id + " .dropDownDiv").attr("id", dropDownID);
	$(document).ready(function() {
		$("#" + id + " #" + activeTabID).click(function(e) {
			return globalMenuHandleClick(e, activeTabID, dropDownID);
			//this draws the submenu on click as well as handling the off click
		});		
	});

	
	var $arrow = $("#" + id + " .arrow");
	dropDownArrowStateInit($arrow);

	//zooms
	$zoomDiv = $("#" + id + " .zoomDiv");
	$zoomDiv.find("a").each(function() {
		//use $(selector).data("zoomCode") to get access to the zoom param to use
		$(this).data("zoomCode", this.className);
		//don't add classes before this line
		$(this).addClass("hover");
		$(this).addClass("colorBlack");
		$(this).attr("href", "javascript:void(0)");
		$(this).click(function(){
			$(this).parent().find("a").removeClass("bold");
			$(this).addClass("bold");

			// Click selected div in dropdown
			$("#" + id + " .dropDownSubmenu div.active").click();
			
			var zoomValue = $(this).data('zoomCode');
			
			// Update csv link for the dropdown linechart			
			$('#chartDownloadCsv').attr('href', urlMap.createReportingUrl('entitiesChartCsv', {zoom : zoomValue, csv : true, "metrics":metricsForCharts}));

			// Update date-based activity table if an entity is selected
			if(config.urlParams.entityId != 0) {
				makeSingleEntitySummaryTable(urlMap.createReportingUrl('entitiesChartCsv', {zoom : zoomValue, csv : false, "metrics": metricsForTables}));
				$('#activityDownloadCsv').attr('href', urlMap.createReportingUrl('entitiesChartCsv', {zoom : zoomValue, csv : true, "metrics": metricsForTables}));
			}
		});
	});
	
	//initialize zoom value
	$zoomDiv.find(" ." + config.urlParams.zoom).addClass("bold");

	//dropdown entries
	function removeDropdownState($this)
	{
		$this.removeClass("inactiveDropDown")
			 .removeClass("activeDropDown")
			 .removeClass("overDropDown");
	}
	function resetDropdown($this)
	{
		removeDropdownState($this);
		if ($this.hasClass("active"))
		{
			$this.addClass("activeDropDown");
		}
		else
		{
			$this.addClass("inactiveDropDown");
		}

	}

	for (var i = 0; i < config.charts.length; i++)
	{
		var div = document.createElement("div");
		$(div).data("data", config.charts[i]);
		
		$(div).html(config.charts[i].name);
		$(div).addClass("inactiveDropDown");
		$(div).hover(
			function() {
				removeDropdownState($(this));
				$(this).addClass("overDropDown");
			}, function() {
				resetDropdown($(this));
			}
		);
		$(div).click(function(){
			var data = $(this).data("data");
			$("#" + id + " #" + activeTabID + " span").html(data.name);

			//zooms
			var zoom = $("#" + id + " .zoomDiv a.bold").data("zoomCode");

			//submenu highlighting
			$(this).parent().find(".active").removeClass("active");
			$(this).addClass("active");
			$(this).parent().find("div").each(function(){resetDropdown($(this));});

			//explain link
			$("#" + id + " .explainLink").attr("href", data.explain);

			//csv link
			$("#" + id + " .csvLink").attr("href", data.csv);

			//save index
			setDropDownIndexStore($(this).parent().find(this).index());
			
			//redrawChart
			var chartSwf = data.chartSwf;
			var url = data.data;
			var hasQMark = url.indexOf("?") > 0;

			for (var name in config.urlParams)
			{
				if (config.urlParams.hasOwnProperty(name)){
					var sym = "&";
					if (!hasQMark)
					{
						sym = "?";
						hasQMark = true;
					}
					url += sym + name + "=" + encodeURIComponent(config.urlParams[name]);
				}
			}
			url = updateUrlParam(url, "zoom", zoom);
			
			drawMainChart(url,id+"_chartDiv", chartSwf);
			$("#" + dropDownID).hide();
		});
		$("#" + id + " .dropDownSubmenu").append(div);
	}

	//initialize
	//hide submenu
	$("#" + dropDownID).hide();
	$("#" + id + " .dropDownSubmenu :eq(" + 0 + ")").click();
}

function showDiv(strId)
{
	document.getElementById(strId).style.display = "block";
	sideNavHeightAdjust();
}
function showInlineDiv(strId)
{
	document.getElementById(strId).style.display = "inherit";
	sideNavHeightAdjust();
}
function showTableDiv(strId)
{
	document.getElementById(strId).style.display = "table";
	sideNavHeightAdjust();
}
function showVisibileDiv(strId)
{
	document.getElementById(strId).style.visibility = "visible";
}
function hideVisibileDiv(strId)
{
	document.getElementById(strId).style.visibility = "hidden";
}
function hideDiv(strId)
{
	if (document.getElementById(strId) == null)
	{
		//alert(strId);
	}
	else
	{
		document.getElementById(strId).style.display = "none";
		sideNavHeightAdjust();
	}
}
function hideTableDiv(strId)
{
	hideDiv(strId);
	sideNavHeightAdjust();
}

function toggleDiv(strId)
{
	if (document.getElementById(strId).style.display == "block" || document.getElementById(strId).style.display == "")
	{
		hideDiv(strId);
	}
	else
	{
		showDiv(strId);
	}
}

function escapeHTML (str)
{
   var div = document.createElement('div');
   var text = document.createTextNode(str);
   div.appendChild(text);
   return div.innerHTML;
}

function escapeSingleQuoteWithBackslash(data){
	return data.replace("'", "\\'");
}

var chartLists = {};
var latestChartRequest = {};
function randomChartID()
{
	var currentTimeMillis = new Date().getTime();
	return "_fsChart" + currentTimeMillis + "_" + Math.floor(Math.random() * 10000000);
}
function drawSparkChart(divId, dataUrl, dataCallback)
{
	var requestId = randomChartID();
	window.latestChartRequest[divId] = requestId;
	$.get(
		dataUrl,
		{ "r" : new Date().getTime() },
		function(data, textStatus, jqXHR){
			if ((data.indexOf("Oops, an unexpected error has occurred") !== -1) || (data.indexOf("Sorry, this action requires an permission level") !== -1)) {
				return;
			}
		
			if (window.latestChartRequest[divId] == requestId) {
				drawSparkChartFromData(divId, data, dataCallback);
			}
		},
		'text'
	);
}
function drawSparkChartFromData(divId, data, dataCallback)
{
	var sparkChart = new FusionCharts("/jsp/charts/swf/SparkLine.swf?PBarLoadingText=%20&XMLLoadingText=%20&ParsingDataText=%20&RenderingChartText=%20", 
										divId+randomChartID(), 
										"80", 
										"26", 
										"0", "0");
	sparkChart.setDataXML(data);		   
	sparkChart.setTransparent(true);		   
	sparkChart.render(divId);
	
	if (dataCallback){
		$("#" + divId)
			.trigger("rendered", [data])
		;
	}
}
function drawMainChartDimensions(dataUrl, divId, width, height, optChartType, dataCallback)
{
	if (optChartType == null || optChartType == '')
	{
		optChartType = "ScrollLine2D.swf";
	}
	if (optChartType != 'DragNode.swf')
	{
		width = "100%";
	}

	sideNavHeightAdjust();

	$("#" + divId).empty();
	
	var requestId = randomChartID();
	window.latestChartRequest[divId] = requestId;
	$.get(
		dataUrl,
		{ "r" : new Date().getTime() },
		function(data){
			if ((data.indexOf("Oops, an unexpected error has occurred") !== -1) || (data.indexOf("Sorry, this action requires an permission level") !== -1)) {
				return;
			}
			if (window.latestChartRequest[divId] == requestId){ 
				var chart = drawMainChartFromData(optChartType, divId, width, height, data, dataUrl, dataCallback);
			
				if (optChartType != 'DragNode.swf'){
					var minSafeMillis = 2500;
					
					var makeSavedChartObj = function(){
						var _lastDraw = 1;
						var _waiting = false;
					
						var pub = {
							"redraw" : function(){
								
								if (new Date().getTime() - _lastDraw > minSafeMillis){
									if ($("#" + chart.id).length > 0){
										chart.render(divId);
									}
									
									_lastDraw = new Date().getTime();
								}else{
									if (!_waiting){
										_waiting = true;
										function stopWaiting(){
											_waiting = false;
										}
										
										var obj = this;
										setTimeout(function(){
											obj.redraw();
											stopWaiting();
										}, minSafeMillis);
									}
								}
							}
						};
						return pub;
					};
					chartLists[divId] = makeSavedChartObj();
				}
			}
		},
		'text'
	);
}

function drawMainChart(dataUrl, divId, optChartType, dataCallback)
{
	drawMainChartDimensions(dataUrl, divId, 640, 320, optChartType, dataCallback);
}

function drawMainChartFromData(optChartType, divId, width, height, data, dataUrl, dataCallback)
{
	var chart = new FusionCharts("/jsp/charts/swf/" + optChartType, divId+randomChartID(), width, height, "0", "0");
	chart.setDataXML(data);
	chart.setTransparent(true);
	
	chart.render(divId);
	
	if (dataCallback){
		$("#" + divId)
			.trigger("rendered", [data])
		;
	}
	sideNavHeightAdjust();//this is actually called so on page load with pages that have tons of charts the footer doesn't load in the middle of the page
	return chart;
}
       	
function makeActiveDropDown(tab)
{
	tab.className = 'activeDropDown';
	tab.onmouseover=function(){tab.className = 'overDropDown'};
	tab.onmouseout=function(){tab.className = 'activeDropDown'};
}


function makeInactiveDropDown(tab)
{
    tab.className = 'inactiveDropDown';
    tab.onmouseover=function(){tab.className = 'overDropDown'};
    tab.onmouseout=function(){tab.className = 'inactiveDropDown'};
}

function emboldenLink(id)
{
	document.getElementById(id).className = "colorBlack bold hover activeLink";
}
function deboldenLink(id)
{
	if (document.getElementById(id) == null)
	{
		//alert(id);
	}
	else
	{
		document.getElementById(id).className = "colorBlack hover";
	}
}
var globalMapDrillDowns = {};
function mapDDUnique(swfUrl, baseUrl, zoomLevel, zoneId, zoneName, uniqueId)
{
	var uniqueFunc = globalMapDrillDowns[uniqueId];
	if (uniqueFunc != undefined){
		uniqueFunc(swfUrl, baseUrl, zoomLevel, zoneId, zoneName);
	}else{
		mapDD(swfUrl, baseUrl, zoomLevel, zoneId, zoneName);
	}
}

var MapUtils = MapUtils || function(){
	//private
	function buildLinkPart(zoneData, baseUrl, isLast){
		var span = $("<span />");
		
		var zoneLink = $("<a />")
			.appendTo(span)
			.addClass("colorMediumBlue bold hover")
			.attr("swffile", zoneData.swf)
			.attr("zoomlevel", zoneData.zoomLevel)
			.attr("zoneId", zoneData.zoneId)
			.html(zoneData.zoneName)
		;
		
		if (!isLast){
			var arrowSpan = $("<span />")
				.append(" &raquo; ")
				.addClass("colorBlack bold")
				.prependTo(span);
			;
			zoneLink.on("click", function(e){
				mapDD(zoneData.swf, baseUrl, zoneData.zoomLevel, zoneData.zoneId, zoneData.zoneName);
			});
		}else{
			//world link
			zoneLink.on("click", function(e){
				resetMap(false);
			});
		}
		
		return span;
	}
	var pub = {
		buildReturnLink : function(zonesList, baseUrl){
			var unusedContainer = $("<div />");
			$.each(zonesList, function(index, zone){
				var part = buildLinkPart(zone, baseUrl, (zonesList.length-index == 1));
				unusedContainer.prepend(part);	//stack order
			});
			
			return unusedContainer.children();
		}
	};
	
	return pub;
}();

/**
 * cookiePrefix can be false in mapDDCore, which means it will be ignored.  
 * currently only using that functionality for a one-off so not updating the logic here
 * theoretically, mapDDSaved should only be called after the cookie is checked
 */
function mapDDSaved(divId, baseUrl, headerParams, width, height, cookiePrefix)
{
	var cookiePrefixSafe = cookiePrefix || "fa_";

	var zoneId = readCookie(cookiePrefixSafe + "map_zoneId");
	var zoomLevel = readCookie(cookiePrefixSafe + "map_zoomLevel");
	
	var savedGeoJsonUrl = new Flurry.UrlParams("/geoSavedJson.do")
		.addParams({
			"zoneId" : zoneId,
			"zoomLevel" : zoomLevel
		})
		.buildUrl()
	;
	$.getJSON(savedGeoJsonUrl, function(data){
		var navLink = MapUtils.buildReturnLink(data, baseUrl);
		$('#mapReturnLink')
			.empty()
			.append(navLink)
		;
	
		if (zoomLevel == 0){
			hideDiv('mapReturnLink');
		} else {
			showDiv('mapReturnLink');
		}
		
		var swfUrl = data[0].swf;
		var xmlUrl = "/" + baseUrl + ".do" + "?" + headerParams + "&zoneId=" + zoneId + "&zoomLevel=" + zoomLevel;
		
		var map = new FusionMaps("/jsp/charts/maps/" + swfUrl, divId+randomChartID(), width, height, "0", "0");
		map.setDataURL(escape(xmlUrl));
		map.setTransparent(true);
		map.render(divId);
	});
}

function mapDDCore(divId, width, height, swfUrl, baseUrl, headerParams, zoomLevel, zoneId, zoneName, cookiePrefix)
{
	var cookiePrefixSafe = cookiePrefix || "fa_";
	var navObj = document.getElementById('mapReturnLink');
	
	var xmlUrl = "/" + baseUrl + ".do" + "?" + headerParams + "&zoneId=" + zoneId + "&zoomLevel=" + zoomLevel;

	if (zoomLevel > 0)
	{
		showDiv('mapReturnLink');
		var span = document.createElement("span");
		
		var spanArrow = document.createElement("span");
		spanArrow.innerHTML = " &raquo; ";
		spanArrow.className = "colorBlack bold";
		
		var link = document.createElement("a");
		link.className = "colorMediumBlue bold hover";
		link.href = "javascript:mapDD('" + swfUrl + "', '" + baseUrl + "', '" + zoomLevel + "', '" + zoneId + "', '" + zoneName + "');";
		link.innerHTML = zoneName;
		
		span.appendChild(spanArrow);
		span.appendChild(link);
		
		while (navObj.childNodes.length > zoomLevel)
		{
			var lastLink = navObj.childNodes[navObj.childNodes.length-1];
			navObj.removeChild(lastLink);
		}
		
		navObj.appendChild(span);
	}
	else
	{
		//strip nav down to worldLevel
		hideDiv('mapReturnLink');
	}
	
	//save these in global cookie
	if (cookiePrefix !== false){
		//zoomLevel
		createCookie(cookiePrefixSafe + "map_zoomLevel", zoomLevel);
		//zoneId
		createCookie(cookiePrefixSafe + "map_zoneId", zoneId);
	}
	
	var map = new FusionMaps("/jsp/charts/maps/" + swfUrl, divId+randomChartID(), width, height, "0", "0");
	map.setDataURL(escape(xmlUrl));
	map.setTransparent(true);
	map.render(divId);
	sideNavHeightAdjust();//this is actually called so on page load with pages that have tons of charts the footer doesn't load in the middle of the page
}

//get width of text
function getWidth(text)
{
	var spanElement = document.createElement("span");
	spanElement.style.whiteSpace = "nowrap";
	spanElement.innerHTML = text;
	document.body.appendChild(spanElement);
	var width = spanElement.offsetWidth;
	document.body.removeChild(spanElement);

	return width;
}

function scaleIphoneText(divEl, maxWidth, maxHeight)
{
	var counter = 0;
	if (divEl.style.fontSize == null || divEl.style.fontSize == "")
	{
		divEl.style.fontSize = "20px";
	}

	var textWidth = divEl.offsetWidth;
	var textHeight = divEl.offsetHeight;

	while (textWidth > maxWidth || textHeight > maxHeight)
	{
		var currentFontSize = divEl.style.fontSize.split("px").join("");
		currentFontSize--;
		divEl.style.fontSize = currentFontSize + "px";

		textWidth = divEl.offsetWidth;
		textHeight = divEl.offsetHeight;

		if (currentFontSize < 10 || counter > 100)
		{
			//something went wrong
			break;
		}
		counter++;
	}
};

var header, headerHeight, 
	footer, footerHeight,
	topArea, topHeight,
	leftColumn, leftHeight,
	centerColumn, centerHeight,
	rightColumn, rightHeight,
	main, mainTrueHeight,
	lastWidth, lastCenterWidth;
var heightsDone = false;
var heightsCounter = 2000;
var _resizeListeners = [];

//be careful about doing too much when this event triggers
//right now everything calls heightAdjust all the time so it will likely fire a lot of times for the slightest page adjustment
function addResizeListener(obj){
	_resizeListeners.push(obj);
}
function fireResizeTriggers(){
	var size = _resizeListeners.length;
	for (var i = 0; i < size ; i++){
		$(_resizeListeners[i]).trigger("resized");	//dont make this "resize" or else you'll be getting an infinite loop, "resize" is a reserved event name for the window redraw
	}
}

function setupHeights()
{
	if ($("#footer").css("position") == "absolute"){
		$("#footer")
			.hide()
			.css("position", "relative")
			.show();
	}
	footer = document.getElementById('footer');
	if (heightsDone == false && footer != null)
	{
		heightsDone = true;
		header = document.getElementById('header');
	
		topArea = document.getElementById('topArea');
	
		headerHeight = header.offsetHeight;
		footerHeight = footer.offsetHeight;
	
		leftColumn = document.getElementById('leftColumn');
		centerColumn = document.getElementById('centerColumn');
		rightColumn = document.getElementById('rightColumn');
	
		main = document.getElementById('main');
	
		lastWidth = $('#body').width();
		lastCenterWidth = $(centerColumn).width();
	
		window.onresize = resizeContainers;
	}
	resizeContainers();
}

var resizeContainersEnabled = true;

function disableResizeContainers() 
{
	resizeContainersEnabled = false;
}

function enableResizeContainers() 
{
	resizeContainersEnabled = true;
}

//optional method to override if additional chart redraw stuff is needed
function optChartRedraw()
{
	//override me!
}
//optional method to override if additional page resize stuff is needed
function optPageResize()
{
	//override me!
}

//method to access the height of the functional area of the page
function getMainAreaHeight(){
    var viewportHeight = document.getElementById('body').offsetHeight;
    if (!headerHeight){
        headerHeight = document.getElementById('header').offsetHeight;
    }
    if (!footerHeight){
        footerHeight = document.getElementById('footer').offsetHeight;
    }
    
    return (viewportHeight - headerHeight - footerHeight - 35 - 35);
}

function resizeContainers()
{
	if (!resizeContainersEnabled) 
	{
		return;
	}
	if (heightsDone)
	{
		var topMarginBottom = 0;
		
		if (topArea)
		{
			topMarginBottom = parseFloat($(topArea).css('marginBottom'),10);
		}
		
		if (leftColumn) leftColumn.style.height = '';
		centerColumn.style.height = '';
		if (rightColumn) rightColumn.style.height = '';
	
		if (topArea)
		{
			topHeight = topArea.offsetHeight;
		}
	
		leftHeight = 0;
		if (leftColumn)
		{
			leftColumn.style.position = 'relative';
				leftHeight = leftColumn.offsetHeight;
			leftColumn.style.position = 'absolute';
			if (topArea && topHeight > 0) {
				leftColumn.style.top = (topHeight + topMarginBottom) + 'px';
			} else {
				leftColumn.style.top = '0px';
			}
			
		}
	
		if (!leftColumn || leftColumn.style.display == 'none')
		{
			centerColumn.style.marginLeft = "35px";
		}
		else
		{
			centerColumn.style.marginLeft = "265px";
		}
	
		centerHeight = centerColumn.offsetHeight;		//guaranteed to be here
	
		rightHeight = 0;
		if (rightColumn)
		{
			rightColumn.style.position = 'relative';
				rightHeight = rightColumn.offsetHeight;
			rightColumn.style.position = 'absolute';
			if (topArea && topHeight > 0){
				rightColumn.style.top = (topHeight + topMarginBottom) + 'px';
			} else {
				rightColumn.style.top = '0px'; 
			}
		}
	
		if (!rightColumn || rightColumn.style.display == 'none')
		{
			centerColumn.style.marginRight = "35px";
		}
		else
		{
			centerColumn.style.marginRight = "265px";
		}
	   
		mainTrueHeight = Math.max(leftHeight, centerHeight, rightHeight);
		if (topArea && topHeight > 0)
		{
			mainTrueHeight += (topHeight + topMarginBottom);
		}
		
		var contentHeight = Math.max(getMainAreaHeight(), mainTrueHeight);
	
		main.style.height = contentHeight + 'px';
		
		if (topArea && topHeight > 0)
		{
			contentHeight -= (topHeight + topMarginBottom);
		}
		
		if (leftColumn) leftColumn.style.height = contentHeight + "px";
		centerColumn.style.height = contentHeight + "px";
		if (rightColumn) rightColumn.style.height = contentHeight + "px";
		
		viewportWidth = document.getElementById('body').offsetWidth;
		
		optPageResize();
		fireResizeTriggers();
		
		var centerWidth = $(centerColumn).width();
		if (viewportWidth != lastWidth || lastCenterWidth != centerWidth)
		{
			lastWidth = viewportWidth;
			lastCenterWidth = centerWidth;
			
			for (var divId in chartLists)
			{
				if (chartLists.hasOwnProperty(divId)){
					chartLists[divId].redraw();
				}
			}
			
			optChartRedraw();
		}
		
		heightsCounter *= 2;
		if (heightsCounter < 35000)
		{
			setTimeout(resizeContainers, heightsCounter);
		}
	}
	else
	{
		setTimeout(setupHeights, 500);
	}
}
function sideNavHeightPrepTable()
{
	resizeContainers();
}

function sideNavHeightAdjust()
{
	resizeContainers();
}

function makeTwoDigits(num)
{
	if (num >= 10)
	{
		return num;
	}
	else
	{
		return "0" + num;
	}
}

function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}

function getFormattedTime()
{
	var a_p = "";
	var d = new Date();
	var curr_hour = d.getHours();
	if (curr_hour < 12)
	{
	   a_p = "AM";
	}
	else
	{
	   a_p = "PM";
	}
	if (curr_hour == 0)
	{
	   curr_hour = 12;
	}
	if (curr_hour > 12)
	{
	   curr_hour = curr_hour - 12;
	}
	
	var curr_min = d.getMinutes();
	
	curr_min = curr_min + "";
	
	if (curr_min.length == 1)
	{
	   curr_min = "0" + curr_min;
	}
	
	return curr_hour + ":" + curr_min + " " + a_p;
}

function IsNumeric(input)
{
   return (input - 0) == input && input.length > 0;
}

function addCommas(nStr)
{
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

function removeCommas(str)
{
	return str.replace(',', '');
}

var maxLength = function(string, max)
{
	var newString = string;
	if (string.length > max)
	{
		newString = string.substr(0, max) + "...";
	}

	return newString;
};

function getUrlParam(url, param) {
    return decodeURIComponent(
        (url.match(RegExp("[?|&]"+param+"=([^&|#]*)"))||[,null])[1]
    );  
};

var updateUrlParam = function (url, param, newValue){
	var noHashUrl = url;
	var hash = "";
	if (url.indexOf("#") >= 0){
		noHashUrl = url.slice(0, url.indexOf("#"));
		hash = url.slice(url.indexOf("#"));
	}
	var urlSplit = noHashUrl.split(param+"=");
	if (urlSplit.length == 2)
	{
		var nextParamStart = urlSplit[1].indexOf("&");
		if (nextParamStart < 0)	//only one param found in url so no &
		{
				return urlSplit[0] + param + "=" + newValue + hash; 
		}
		else
		{
			return urlSplit[0] + param + "=" + newValue + urlSplit[1].substring(nextParamStart) + hash;
		} 
	}
	else if (urlSplit.length == 1)
	{
		var appendString = "";
		if (url.indexOf('?') < 0)
		{
			appendString = "?";
		}
		else
		{
			appendString = "&";
		}
		appendString += param + "=" + newValue;
		return noHashUrl + appendString + hash;
	}
	return url;
}
function confirmDelete(msg, url)
{
	if (confirm(msg))
	{
		window.location.href = url;
		return true;
	}
	else
	{
		return false;
	}
}

function switchFieldInput(divToHide, divToShow)
{
	toggleDiv(divToHide);
	toggleDiv(divToShow);
}

function loadSideNavLinks(navLinks, linkID, subLinkID){

	for (var i = 0, size = navLinks.length; i < size; i++){
		var sideNavDiv = document.getElementById("sideNavArea");
		var relDiv = document.createElement("div");
		relDiv.className = "relative";

		var linkNode = document.createElement("a");
		linkNode.href = navLinks[i].href;
		if (navLinks[i].target){
			$(linkNode).attr("target", navLinks[i].target);
		}
		if (navLinks[i].title){
			$(linkNode).attr("title", navLinks[i].title);
		}

		var linkClassName = navLinks[i].linkClass || "mainLink";

		var linkActive = (navLinks[i].id == linkID);
		if (linkActive){
			linkClassName = "mainLinkActive";
		}
		
		linkNode.className = linkClassName;

		var linkName = document.createTextNode(navLinks[i].name);

		linkNode.appendChild(linkName);

		var linkImg = navLinks[i].img;
		if (linkImg != null){
			var imgSpan = document.createElement("span");
			imgSpan.innerHTML = " " + linkImg;
			linkNode.appendChild(imgSpan);
		}
	
		relDiv.appendChild(linkNode);

		sideNavDiv.appendChild(relDiv);
		
		var children = navLinks[i].children;
		if (linkActive && children != null && children.length > 0){
			var childDiv = document.createElement("div");
			childDiv.className="subNav";
			for (var j = 0 ; j < children.length ; j++){
				var child = children[j];
				if (child.id){ //otherwise empty link
					
					var childLink = $("<a />");
					if (child.disabled){
						childLink.click(function(e){
							e.stopPropagation();
							e.preventDefault();
						});
						childLink.addClass("disabled");
					}else{
						childLink.attr("href", child.href);
					}
					if (child.title){
						childLink.attr("title", child.title);
					}
	
					var childLinkClassName = "subLink";
					
					var childLinkActive = (child.id == subLinkID);
					if (childLinkActive){
						childLinkClassName = "subLinkActive";
					}
					childLink.addClass(childLinkClassName);
					if (child.nameLong != null){
						var longName = child.nameLong;
						childLink.append('<span class="longName">' + longName + '</span>');
					}else{
						childLink.text(child.name);
					}
					
					var linkImg = child.img;
					if (linkImg != null){
						var imgSpan = $("<span />");
						imgSpan
							.text(" ")
							.append(linkImg)
						;
						childLink.append(imgSpan);
					}
	
					$(childDiv).append(childLink);
				}
			}
			
			//search boxes
			var searchBox = navLinks[i].searchBox;
			if (searchBox != null){
				appendSideNavSearchBox(childDiv, searchBox);
			}
			
			sideNavDiv.appendChild(childDiv);
			
			if (searchBox != null){
				activateSideNavSearchBox(searchBox);
			}
			
			$(sideNavDiv).find(".longName").each(function(){
				var span = $(this);
				var name = span.text();
				
				var originalHeight = span.height();
				span.text("&nbsp;");
				var baseHeight = span.height();
				if (originalHeight > baseHeight){
					span.attr("title", name);
					for (var i = 0 ; i < name.length ; i++){
						var shortName = name.substring(0, name.length - (i+1)) + "...";
						span.text(shortName);
						if (span.height() <= baseHeight){
							break;
						}
					}
				}else{
					span.text(name);
				}
				
				var link = span.parents("a");
				if (!link.is("[title]")){
					link.attr("title", name);
				}
			});
		}else if (children == null || children.length == 0){
			$(linkNode).addClass("noChildren");
		}
	}
}

function appendSideNavSearchBox(parentDiv, searchBoxData)
{
	var allDiv = document.createElement("div");
	var showHighlight = searchBoxData.highlight;
	allDiv.className = "searchBox";
	if (searchBoxData.marginsOff)
	{
		allDiv.style.margin = "0px";
	}
	 
	var blankDiv = document.createElement("div");
	
	var textNode = document.createTextNode(searchBoxData.text);
	blankDiv.appendChild(textNode);
	
	var searchACDiv = document.createElement("div");
	searchACDiv.id = "searchAC";
	searchACDiv.style.zIndex = "99";
	
	var searchACInput = document.createElement("input");
	searchACInput.id = "searchACInput";
	searchACInput.type = "text";
	searchACDiv.appendChild(searchACInput);
	
	blankDiv.appendChild(searchACDiv);
	
	allDiv.appendChild(blankDiv);
	parentDiv.appendChild(allDiv);
}

function activateSideNavSearchBox(searchBoxData)
{
	var inputBox = $("#searchACInput");
	var ac = new Flurry.Widgets.AutoComplete(inputBox, {
		"schema" : {
			"fields" : searchBoxData.dataSchema
		},
		"data" : searchBoxData.dataAC,
		"hint" : "type to search..."
	});
	inputBox.on("typeahead.update", searchBoxData.acLoadPage);
}

function defaultEmpty(str) 
{
	if (str == undefined || str == null) {
		return "";
	} else {
		return str;
	}
}

if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}


function AutoCounter(start) {
    var count = 0;
    if (start){
    	count = start;
    }
    return {
        get: function() {
            return this.increment();
        },
        val : function(){
        	return count;
        },
        increment : function(){
        	return count++;
        },
        decrement : function(){
        	return count--;
        }
    }
}

function confirmUnload(testFn, text){
	window.onbeforeunload = function (e){
		if (testFn()){
			var e = e || window.event;
			// For IE and Firefox before version 4
			if (e) {
				e.returnValue = text;
			}
			
			//webkit
			return text;
		}
	};
}

$(document).ready(function(){
	//attach browser specific className to body
	if (!$.support.opacity){ //IE
		$("body").addClass("ie");
	}	
	
	//create flurry button handlers
	
	var makeHandlers = function($this){
		//wrap text node
		var startClass = "normal";
		if (!($this.hasClass("normal") || $this.hasClass("highlight") || $this.hasClass("disabled"))){
			$this.addClass("normal");
		}else if ($this.hasClass("normal")){
			startClass = "normal";
		}else if ($this.hasClass("highlight")){
			startClass = "highlight";
		}else if ($this.hasClass("disabled")){
			startClass = "disabled";
			$this.attr("disabled", true);		//disable interaction too
		}
		var isDisabled = function($this){
			return $this.is(":disabled") || $this.hasClass("disabled");
		};
		$this.on({
			mouseenter: function(e){
				if (!isDisabled($this)){
					$this.addClass("hover");
				}
			},
			mouseleave: function(e){
				if (!isDisabled($this)){
					$this.removeClass("hover");
					$this.removeClass("down");
				}
			}, 
			mousedown: function(e){
				if (!isDisabled($this)){
					$this.addClass("down");
				}
			},
			mouseup: function(e){
				if (!isDisabled($this)){
					$this.removeClass("down");
				}
			},
			click: function(e){
				if (isDisabled($this)){
					e.preventDefault();
				}
			}
		});
	};
	// Currently 2 ways to opt out of the span wrapper
	/*var buttons = $("button:not(.noSpanButton, .ui-datepicker-trigger, .yui-cal-nav-btn button, .noButton *)");
	buttons.livequery(function(){
		var $this = $(this);
		if ($this.parents(".yui-button").length <= 0){
		
			if ($this.find("span").length <= 0){
				$this.wrapInner("<span/>");
			}
			if ($this.hasClass("addButton")){
				$this.find("span").prepend('<img src="/images/icons/add_blue.png"/>');
			}
			makeHandlers($this);
		}
	});
	
	var inputButtons = $("input[type='button'], input[type='submit']").not('big');
	inputButtons.livequery(function(){
		$(this).addClass("button fixed");
		makeHandlers($(this));
	});*/
});
function disableButton($button){
	$button.attr("disabled", true);
	$button.addClass("disabled");
}
function enableButton($button){
	$button.attr("disabled", false);
	$button.removeClass("disabled");
}
function makeHint(inputText){
	if (inputText.val() == "" && inputText.attr("title")){
		inputText.addClass("textPrefill");
		inputText.val(inputText.attr("title"));
	}
}
function removeHint(inputText){
	if (inputText.attr("title") && inputText.val() == inputText.attr("title")){
		inputText.val("");
	}
	inputText.removeClass("textPrefill");
}

function getPlatformImg(platformName, conf){
	var config = conf || {};
	var useNewWindowsPhoneGraphics = config.useNewWindowsPhoneGraphics || false;
	var uiRefresh = config.uiRefresh || false;
	
	var img = $("<img />");
	var uniqueStr = "";
	var title = "";
	if (platformName == "Android") {
		uniqueStr = "android";
		title = "Android";
	} else if (platformName == "iPhone") {
		uniqueStr = "iphone";
		title = "iPhone";
	} else if (platformName == "iPad") {
		uniqueStr = "ipad";
		title = "iPad";
	} else if (platformName == "WindowsPhone") {
		if (useNewWindowsPhoneGraphics){
			uniqueStr = "windowsphone";
			title = "Windows Phone";
		}else{
			uniqueStr = "windowsphone_old";
			title = "Windows Phone";
		}
	} else if (platformName == "Blackberry" || platformName == "BlackberrySDK") {
		uniqueStr = "blackberry";
		title = "Blackberry";
	} else if (platformName == "JavaME" || platformName == "JavaMESDK") {
		uniqueStr = "j2me";
		title = "JavaME";
	} else if (platformName == "HTML5") {
		uniqueStr = "html5";
		title = "Mobile Web";
	} else if (platformName == "Flash") {
		uniqueStr = "flash";
		title = "Flash";
	} else if (platformName == "OSX") {
		uniqueStr = "osx";
		title = "OSX";
	} else if (platformName == "Windows") {
		uniqueStr = "windows";
		title = "Windows";
	}
	var imgString = "/images";
	var isUpdatedPlatform = (uniqueStr == "android" || uniqueStr == "ipad" || uniqueStr == "iphone");
	if (uiRefresh && isUpdatedPlatform){
		imgString += "/uiRefresh"
	}
	
	if (uniqueStr.length > 0){
		var imgUrl = imgString + "/logos/" + uniqueStr + "_icon_small.png";
		img
			.attr("src", imgUrl)
			.addClass("platformIconImg")
			.attr("border", "0px")
			.attr("title", title)
		;
		return img.wrap("<div />").parent().html();
	}
	
	return "";
}
function convertHexCssToRgb(hex, optAlpha){
	//http://www.javascripter.net/faq/hextorgb.htm
	function hexToR(h) {return parseInt((cutHex(h)).substring(0,2),16)}
	function hexToG(h) {return parseInt((cutHex(h)).substring(2,4),16)}
	function hexToB(h) {return parseInt((cutHex(h)).substring(4,6),16)}
	function cutHex(h) {return (h.charAt(0)=="#") ? h.substring(1,7):h}
	
	var open = ((optAlpha == null) ? "rgb" : "rgba") + "(";
	var close = ((optAlpha == null) ? "" :"," + optAlpha) + ")";
	return open + hexToR(hex) + "," + hexToG(hex) + "," + hexToB(hex) + close;
}

// http://jsfiddle.net/xgJ2e/2/ from http://stackoverflow.com/questions/11849308/generate-colors-between-red-and-green-for-an-input-range
function hsv2rgb(h, s, v) {
	var rgb, i, data = [];
	if (s === 0) {
		rgb = [v,v,v];
	} else {
		h = h / 60;
		i = Math.floor(h);
		data = [v*(1-s), v*(1-s*(h-i)), v*(1-s*(1-(h-i)))];
		switch(i) {
			case 0:
				rgb = [v, data[2], data[0]];
				break;
			case 1:
				rgb = [data[1], v, data[0]];
				break;
			case 2:
				rgb = [data[0], v, data[2]];
				break;
			case 3:
				rgb = [data[0], data[1], v];
				break;
			case 4:
				rgb = [data[2], data[0], v];
				break;
			default:
				rgb = [v, data[0], data[1]];
			break;
		}
	}
	return '#' + rgb.map(function(x){
		return ("0" + Math.round(x*255).toString(16)).slice(-2);
	}).join('');
}
//val should be 1-100
function scaleColor(val){
	function scale(range, percent){
		return range[0] + Math.round(percent * (range[1] - range[0]));
	}
	var hR = [  0, 114];
	var sR = [.83, .73];
	var vR = [.83, .73];
	var p = val / 100.0;
	
	var h = scale(hR, p);
	var s = scale(sR, p);
	var v = scale(vR, p);
	
    return hsv2rgb(h, s, v);
}

(function( $ ) {
  $.fn.defaultIfInvokeEmpty = function(methodName, defaultValue) {
  	if (this.length != 0)
  	{
  		return this[methodName]();
  	} 
  	else
  	{
  		return defaultValue;
  	}
  };
})( jQuery );

if (typeof String.prototype.startsWith != 'function') {
	String.prototype.startsWith = function (str, ignoreCase) {
		var f = function(x){
			return x;
		};
		if (ignoreCase){
			f = function(x){
				return x.toUpperCase();
			};
		}
		
		return f(this).lastIndexOf(f(str), 0) === 0;
	}
}
if (typeof String.prototype.endsWith != 'function') {
	String.prototype.endsWith = function(str, ignoreCase)
	{
		var f = function(x){
			return x;
		};
		if (ignoreCase){
			f = function(x){
				return x.toUpperCase();
			};
		}
		
		return (str == "") || f(this).slice(-str.length) == f(str);
	};
}

String.prototype.trim = function(){
  return this.replace(/(^\s*|\s*$)/g,'');
};

String.prototype.replaceAll = function(pattern, replacement){
	var regex = new RegExp(pattern, "g");
	return this.replace(regex, replacement);
}

String.prototype.htmlUnescape = function () {
	var str = this;
	str = str.replace( /\&apos;/g, "'" );
	str = str.replace( /\&#39;/g, "'" );
	str = str.replace( /\&quot;/g, '"' );
	str = str.replace( /\&lt;/g, "<" );
	str = str.replace( /\&gt;/g, '>' );
	str = str.replace( /\&amp;/g, '&' );
	return str;
}

String.prototype.htmlEscape = function () {
 		return(                                                                 
            this.replace(/&/g,'&amp;').                                         
                replace(/>/g,'&gt;').                                           
                replace(/</g,'&lt;').                                           
                replace(/"/g,'&quot;')                                         
        );   
}

