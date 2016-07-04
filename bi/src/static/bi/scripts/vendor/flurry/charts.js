window.Flurry = window.Flurry || {};

Flurry.DataUrls = Flurry.DataUrls || function(){
	var kData = "data";
	var kCsv = "csv";
	var kExplain = "explain";
	var kSwf = "swf";
	var kReport = "report";
	var kSpark = "spark";
	var kSideBox = "sidebox";
	
	var kName = "name";
	var kCount = "count";
	
	var map = {};

	var doParam = function(value, key){
		if (value == undefined){
			return map[key];
		}
		map[key] = value;
		return pub;
	};
	
	var buildExplainLink = function(folder, page){
		return "javascript:showExplanation('" + folder + "','" + page + "');";
	};
	
	var pub = {
		data : function(url){
			return doParam(url, kData);
		},
		spark : function(url){
			return doParam(url, kSpark);
		},
		//intended to be a complex object
		sidebox : function(obj){
			return doParam(obj, kSideBox);
		},
		csv : function(url){
			return doParam(url, kCsv);
		},
		explain : function(folder, page){
			var url = undefined;
			if (folder != undefined && page != undefined){
				url = buildExplainLink(folder, page);
			}
			
			return doParam(url, kExplain);
		},
		swf : function(url){
			return doParam(url, kSwf);
		}, 
		report : function(url){
			return doParam(url, kReport);
		},
		name : function(obj){
			return doParam(obj, kName);
		},
		count : function(obj){
			return doParam(obj, kCount);
		},
		getMap : function(){
			return map;
		},
		copy : function(otherMap){
			map = $.extend(true, {}, otherMap);
			return this;
		},
		clone : function(){
			return new Flurry.DataUrls().copy(this.getMap());
		}
	};
	
	//default value for swf is scrollLine
	
	pub.swf("ScrollLine2D.swf");
	
	return pub;
}

/**
 * renders the html when given a parent div
 * 
 * lots of subclasses for various ui elements, each of these elements should 
 * take a parent div, return its main owning div, and know how to initialize itself
 */
Flurry.ChartFrame = Flurry.ChartFrame || function(divId){
	/* private variables */
	var fRootDiv = $("#" + divId);
	fRootDiv.addClass("chartArea");

	var kSideMargins = 20;
	
	var framePublic = {};
	
	/** TITLES START **/
	
	framePublic.TitleLinks = function($parent){
		//title right area
		var fParent = $parent;
		
		var checkTitleRightSpace = function(){
			fParent.find(".innerRight a")
				    .css("marginRight", "15px");
		};
		var makeInnerRight = function(){
			if (fParent.find(".innerRight").length <=0){
				fParent.append('<div class="innerRight"></div>');
			}
		};
		
		makeInnerRight();
		
		var titlePublic = {
			addToggle : function(toggleFunction){
				var a = getToggleLink(toggleFunction);
				
				checkTitleRightSpace();
				
				fParent.find(".innerRight").append(a);
				return this;
			},
			addCsv : function(url){
				var a = getCsvLink(url);
				
				checkTitleRightSpace();
				
				fParent.find(".innerRight").append(a);
				return this;
			},
			addExplain : function(url){
				var a = getExplainLink(url);
				
				checkTitleRightSpace();
				
				fParent.find(".innerRight").append(a);
				return this;
			},
			addReport : function(url){
				var a = getReportLink(url);
				
				checkTitleRightSpace();
				
				fParent.find(".innerRight").append(a);
				return this;
			},
			clear : function(){
				fParent.find(".innerRight").html("");
				return this;
			},
			updateParam : function(param, value){
				var links = fParent.find(".innerRight a");
				
				links.each(
					function(){
						var href = $(this).attr("href");
						if (href && href.indexOf("javascript") < 0){
							$(this).attr("href", updateUrlParam(href, param, value));
						}
					}
				);
				
				return this;
			}
		};
		return titlePublic;
	};
	
	/* private methods */
	var getExplainLink = function(url){
		var options = {
			"url" : url,
			"title" : "Explain This Chart",
			"text" : "Explain",
			"img" : "/images/icons/help.gif"
		};
		return getGenericLink(options);
	};
	var getCsvLink = function(url){
		var options = {
			"url" : url,
			"title" : "Download CSV",
			"text" : "Download CSV",
			"img" : "/images/icons/csv.gif",
			"newWindow" : true
		};
		return getGenericLink(options);
	};
	var getReportLink = function(url){
		var options = {
			"url" : url,
			"title" : "View Report",
			"text" : "View Report",
			"img" : "/images/icons/table_go.gif"
		};
		return getGenericLink(options);
	};	
	var getToggleLink = function(toggleFunction){
		var options = {
			"title" : "Toggle This Chart",
			"text" : "Toggle",
			"img" : "/images/icons/table_go.gif", //table_go.gif is a placeholder
			"clickFunction" : toggleFunction
		};
		return getGenericLink(options);
	};
	var getGenericLink = function(options){
		var a = document.createElement("a");
		a.className = "imgHover colorMediumBlue";
		a.title = options.title;
		if (options.url){
			a.href = options.url;
		}
		if (options.newWindow){
			a.target = "_blank";
		}
		
		
		var span = document.createElement("span");
		span.innerHTML = options.text;
		if (options.clickFunction){
			$(span).click(options.clickFunction);
		}
		a.appendChild(span);
		
		var space = document.createTextNode(" ");
		a.appendChild(space);
		
		var img = document.createElement("img");
		img.src = options.img;
		img.border = "0px";
		a.appendChild(img);
		
		return a;
	};
	
	framePublic.SingleTitle = function(){
		//single title
		var makeTitle = function (){
			if (fRootDiv.find(".chartTitle").length <= 0){
				fRootDiv.prepend(
					'<div class="chartTitle relative">' 
				+ 		'<div class="title relative">'
				+			'<div class="inner"></div>'
							//innerRight here
				+		'</div>'
				+	'</div>'
				);
			}
		};
		makeTitle();
		var titlePublic = {
			//title left area
			addSpark : function(url, name){
				YAHOO.util.Connect.asyncRequest('GET', url, {
					success: function(o) 
					{
						if (o.status == 200 && o.responseText != undefined)
						{
							var html = o.responseText;
							fRootDiv.find(".title .inner")
								   .html(html);
							
							if (name){
								fRootDiv.find(".title .inner .sparkDiv .sparkType").html(name);
							}
						}
					},
					failure: function(o) {/* do nothing */}
				});
				return this;
			},
			addCount : function(count){
				if (fRootDiv.find(".title .inner .sparkDiv .sparkNumber").length <= 0){
					var name = fRootDiv.find(".title .inner").html();
					fRootDiv.find(".title .inner").html(
							'<div class="inlineBlock sparkDiv">'
							+		'<span class="sparkNumber"></span>'
							+		' &nbsp; '
							+		'<span class="sparkType"></span>'
							+	'</div>'
					);
					if (name.length > 0){
						fRootDiv.find(".title .inner .sparkDiv .sparkType").html(name);
					}
				}

				fRootDiv.find(".title .sparkDiv .sparkNumber")
						.append(count);
				return this;
			},
			addName : function(name, forceReplace){
				var spark = fRootDiv.find(".title .sparkDiv .sparkType");
				if (spark.length > 0 && !forceReplace){
					spark.html(name);
				}else{
					fRootDiv.find(".title .inner").html('<span class="basic">' + name + '</span>');
				}
				return this;
			},
			addHtml : function($obj){
				fRootDiv.find(".title .inner").append($obj);
				return this;
			},
			clear : function(){
				fRootDiv.find(".chartTitle").remove();
				makeTitle();
				return this;
			},
			//does not return this
			makeTitleLinks : function(){
				var $innerRightDiv = fRootDiv.find(".title");
				return new framePublic.TitleLinks($innerRightDiv);
			},
			isSingleTitle : function(){
				return true;
			}
		};
		return titlePublic;
	};
	
	framePublic.DropDownTitle = function(){
		var fParamMap = {};
		var fActiveTab = null;
		var fDropDown = null;
		(function init(){
			if (fRootDiv.find(".chartTitle").length <= 0){
				fRootDiv.prepend(
					'<div class="chartTitle relative">' 
				+ 		'<div class="dropDownActiveTab relative">'
				+			'<span></span>'
				+			'<div class="inlineBlock arrow">'
				+				'<img border="0px" />'
				+			'</div>'
				+		'</div>'
						//innerRight here
				+	'</div>'
				
				+	'<div class="relative dropDownDiv">'
				+		'<div class="dropDownSubmenu"></div>'
				+	'</div>'
				);
				
				//global click handler - allows you to click off the menu anywhere have hide the menu
				fActiveTab = fRootDiv.find(".dropDownActiveTab");	//top row thats always displayed with arrow
				fDropDown = fRootDiv.find(".dropDownDiv");			//submenu thats sometimes hidden
				
				var activeTabId = "dropDownActiveTab_" + divId;
				var dropDownId = "dropDownDiv_" + divId
				
				fActiveTab.attr("id", activeTabId);
				fDropDown.attr("id", dropDownId);
				
				$(document).ready(function() {
					fActiveTab.click(function(e) {
						return globalMenuHandleClick(e, activeTabId, dropDownId);
						//this draws the submenu on click as well as handling the off click
					});		
				});
				
				var $arrow = fActiveTab.find(".arrow");
				dropDownArrowStateInit($arrow);
				
				//initialize
				//hide submenu
				fDropDown.hide();
			}
		}());
		
		//dropdown entries
		var removeDropdownState = function($this)
		{
			$this.removeClass("inactiveDropDown")
				 .removeClass("activeDropDown")
				 .removeClass("overDropDown");
		};
		var resetDropdown = function($this)
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
		};
		
		var dropDownPublic = {
			//function handleClick used to update the various chart objects 
			//whose urls need to be updated when a new chart is clicked
			addChart : function(name, handleClick){
				var div = document.createElement("div");
				var $span = $("<span />");
				
				$(div).data("name", name);
				$span.html(name).appendTo($(div));
				
				$(div).addClass("inactiveDropDown");
				$(div).hover(
					function() {
						removeDropdownState($(this));
						$(this).addClass("overDropDown");
					}, function() {
						resetDropdown($(this));
					}
				);
				
				$(div).on("menuAction", function(){
					fRootDiv.find(".dropDownActiveTab span").html($(this).data("name"));
					var nameWidth = Math.max($(this).find("span").outerWidth(),  
							fRootDiv.find(".dropDownActiveTab span").outerWidth());
					var arrowWidth = fRootDiv.find(".dropDownActiveTab .arrowDiv").outerWidth();
					fRootDiv.find(".dropDownActiveTab").css({
						"width" : (nameWidth + arrowWidth) + "px",
					});

					//submenu highlighting
					$(this).parent().find(".active").removeClass("active");
					$(this).addClass("active");
					$(this).parent().find("div").each(function(){resetDropdown($(this));});

					//save index - this is an artifact from the original analytics pages
					var index = $(this).parent().find(this).index();
					if (setDropDownIndexStore){
						setDropDownIndexStore(index);
					}
					fRootDiv.trigger("indexUpdate", [index]);
					if (handleClick != null){
						handleClick(fParamMap);
					}
				})
				.click(function(){
					$(this).trigger("menuAction");
				});
				
				fRootDiv.find(".dropDownSubmenu").append(div);
				return this;
			},
			loadChart : function(startIndex){
				var index = startIndex;
				//populate ith choice
				if (index == null || index < 0 || fRootDiv.find(".dropDownSubmenu div").length <= index)
				{
					//bad index, click the active one
					var active = fRootDiv.find(".dropDownSubmenu div.activeDropDown");
					if (active.length > 0){
						active.trigger("menuAction");
						return this;
					}
					index = 0;
				}
				fRootDiv.find(".dropDownSubmenu :eq(" + index + ")").trigger("menuAction");

				return this;
			},
			clear : function(){
				fRootDiv.find(".chartTitle").remove();
				makeTitle();
				return this;
			},
			addParam : function(param, value){
				fParamMap[param] = value;
				return this;
			},
			removeParam : function(param){
				delete fParamMap[param];
				return this;
			},
			hide : function(){
				if (fDropDown.is(":visible")){
					fActiveTab.click();
				}
				return this;
			},
			//does not return this
			makeTitleLinks : function(){
				var $innerRightDiv = fRootDiv.find(".chartTitle");
				return new framePublic.TitleLinks($innerRightDiv);
			},
			isSingleTitle : function(){
				return false;
			},
			setName : function(name, index){
				var dropDownIndex = index || 0;
				
				var $activeTab = fRootDiv.find(".dropDownActiveTab span");
				var $dropDownMenuTab = fRootDiv.find(".dropDownSubmenu :eq(" + index + ")");
				
				if($activeTab.html() == $dropDownMenuTab.html()){
					$activeTab.html(name);
				}
				$dropDownMenuTab.html(name);
				$dropDownMenuTab.data("name", name);
			}
		};
		
		return dropDownPublic;
	};
	
	/* FOOTER */
	framePublic.Footer = function(defaultBenchId){
		var makeChartBottom = function (){
			if (fRootDiv.find(".chartBottom").length > 0){
				fRootDiv.find(".chartBottom").show();
			}else{
				fRootDiv.find(".borderChart")
						.css("marginBottom", "0px");
				
				fRootDiv.append('<div class="chartBottom"></div>')
						.show();
			}
		};
		var makeOption = function(value, name, selected){
			var str =	'<option value="' + value + '"';
			if (selected){
				str += 		' selected="selected"'
			}
			str 	+=	'>'
					+		name
					+	'</option>';
			return str;
		};
		var makeBenchmarkBottom = function(){
			var bottom = fRootDiv.find(".chartBottom");
			if (bottom.find(".bottomBar").length <= 0){
				bottom.prepend('<div class="bottomBar relative"></div>');
				bottom.find(".bottomBar")
				  .append(
				  		'<span class="benchName bold colorDarkGray"></span>'
					+	'<div class="benchText inlineBlock" style="margin-left:40px;"></div>'
				  );
			}
		};
		
		var getCategoryName = function(select, thisCat){
			var name = select.find("option:selected").text();
			if (thisCat != null && select.val() == thisCat.id){
				name = thisCat.name;
			}
			return name;
		}
		
		makeChartBottom();
		var footerPublic = {
			checkBottomSpacing : function(){
				fRootDiv.find(".borderChart")
					.css("marginBottom", "0px");
				
				return this;
			},
			/* BottomText */
			addBottomText : function(msg){
				fRootDiv.find(".chartBottom")
						.append(
							'<div class="bottomText colorMediumBlue" style="text-align:right;margin-top:2px;font-size:9px;">'
						+	'<div class="bottomText colorDarkGray" style="text-align:right;margin-top:2px;font-size:9px;line-height:16px;vertical-align:middle;">'
					    +		'<img src="/images/icons/information-white.png" style="vertical-align:middle;position:relative;top:-2px;"/> '
				    	+		msg
				    	+ 	'</div>'
				    	)
				    	.show();
				return this;
			},
			clearBottomText : function(){
				fRootDiv.find(".chartBottom .bottomText")
						.html("");
				return this;
			},
			
			/* Benchmarks */
			addBenchmarkExplain : function(){
				makeBenchmarkBottom();
				var bottom = fRootDiv.find(".chartBottom");
				bottom.find(".bottomBar").prepend('<div class="innerRight"></div>')
					  .find(".innerRight").append(getExplainLink("javascript:showExplanation('analytics','benchmarking');"));
				return this;
			},
			addBenchmarkExcluded : function(){
				makeBenchmarkBottom();
				var bottom = fRootDiv.find(".chartBottom");
				bottom.find(".benchName")
					  .html('Compare to:');
				bottom.find(".benchText")
					  .append('<span class="bold">This Application is Excluded from Benchmarks</span>');
				return this;
			},
			addAppStoreRequest : function(appStoreName, explanationKey, projectId, savedAppStoreId){
				makeBenchmarkBottom();
				var bottom = fRootDiv.find(".chartBottom");
				var lightboxId = divId + "AppStoreBox";
				var box = new Flurry.Lightbox(lightboxId)
					.setTitle('Enter your ' + appStoreName + ' ID')
					.setBody(
						'In order to display the appropriate benchmark data, we need to associate your application with the ' + appStoreName + '.'  
					+ 	'<br/><br/>'
					+	'<div class="platformExplain"></div>'
					+ 	'<br/>'
					+	'<span style="font-weight:bold;font-size:10px;">Enter ' + appStoreName + ' ID:</span>'
					+	' &nbsp; '
					+	'<input type="text" class="inputText" style="width:200px;font-size:9px;" maxlength="200" value="' + savedAppStoreId + '" />'
					+	' &nbsp; '
					+	'<div class="inlineBlock">'
					+ 		'<button  value="" '
					+			 'class="flurryButton normal" ' 
					+			 'onmousedown="this.className=\'flurryButton down\'" ' 
					+			 'onmouseout="this.className=\'flurryButton normal\'" ' 
					+			 'onmouseup="this.className=\'flurryButton normal\'" '
					+			 'onmouseover="this.className=\'flurryButton normalHover\'" '
					+		'>'
					+			'<span>'
					+				'Update'
					+			'</span>'
					+		'</button>'
					+	'</div>'
					)
				;
				
				var button = $("#" + lightboxId + " .bd button");
				button.click(
					function(){
						var appStoreId = $("#" + lightboxId + " .bd input.inputText").val();
						var url = "/updateProjectAppStoreID.do?projectID=" + projectId + "&appStoreID=" + appStoreId;

						YAHOO.util.Connect.asyncRequest('POST', url, {
							success: function(o) {
								window.location.reload();
							},
							failure: function(o) {alert('There was an error processing your request.  Please try again.');}
						});
					}
				);
				
				var section = 'appCircle';
				var topic = explanationKey;
				var sUrl = 'getExplanation.do?section='+section+'&topic='+topic;

				YAHOO.util.Connect.asyncRequest('GET', sUrl, {
					success: function(o) {
						if(o.responseText !== undefined){
							var explainDiv = $("#" + lightboxId + " .platformExplain");
							explainDiv.html(o.responseText);
							explainDiv.html(explainDiv.find("div.bd").html());
						}
					},
					failure: function(o) {alert('Could not load help.');}	
				});
				
				bottom.find(".benchName")
					  .html('Get Benchmarks!');
				bottom.find(".benchText")
					  .append(
							'<a href="javascript:void(0);" class="spanHover bold colorMediumBlue" style="font-size:9px;">'
							+		'<span>Enter your ' + appStoreName + ' ID</span>'
							+	'</a>'
					  )
					  .find("a").click(
							function(){
								box.toggle();
							}
					  );
				return this;
			},
			addBenchmarkCheckbox : function(defaultCheck, name, updateBenchFn){
				makeBenchmarkBottom();
				
				var div = fRootDiv.find(".chartBottom .benchText");
				div.css("marginLeft", "");
				div.css("fontWeight", "bold");
				div.addClass("colorDarkGray");
				var checkbox = $('<input type="checkbox" style="vertical-align:middle;"/>');
				var checkId = divId + "BenchCheckbox";
				checkbox.attr("id", checkId);
				checkbox.appendTo(div);
				
				var checked = (defaultCheck) ? true : false;
				checkbox.get().checked = checked;
				
				div.append(' <label for="' + checkId + '" style="position:relative;top:2px;margin-left:5px;">'
					+		name
					+	'</label>'
				);
				
				//onchange function
				checkbox.change(function(){
					var checked = this.checked;
					if (updateBenchFn != undefined){
						updateBenchFn(checked);
					}
				});

				//initalize
				if (updateBenchFn != undefined){
					updateBenchFn(checked);
				}
				
				return this;
			},
			addBenchmarkNormal : function(thisCat, categories, updateBenchFn, defaultBenchId){
				makeBenchmarkBottom();
				var savedBenchId = 0;
				if (defaultBenchId != undefined){
					savedBenchId = defaultBenchId;
				}
				//make name
				var bottom = fRootDiv.find(".chartBottom");
				bottom.find(".benchName")
					  .html('Compare to:');
				//make select
				var selectBar = bottom.find(".bottomBar .benchText");
				selectBar.append(
					'<select style="font-size:10px;'
				+					'width:200px;">'
				+	'</select>'
				);
				var select = selectBar.find("select");
				//choose cat
				select.append(
					makeOption(0,'Choose Category...')
				);
				//same as this
				if (thisCat != null){
					select.append(
						makeOption( "0" + thisCat.id,
									thisCat.name + ' (same as this app)',
									true)
					);
				}
				//overall
				select.append(
					makeOption(-1,'Overall')
				);
				//list of categories
				if (categories != null){
					for (var i in categories)
					{
						if (categories.hasOwnProperty(i)){
							select.append(
								makeOption(categories[i].id,
										   categories[i].name)
							);
						}
					}
				}
				//onchange function
				select.change(function(){
					var value = $(this).val();
					savedBenchId = value;

					var name = getCategoryName($(this), thisCat);
					if (updateBenchFn != undefined){
						updateBenchFn(savedBenchId, name);
					}
				});

				//initalize
				if (savedBenchId != 0){
					select.find("option[value='" + savedBenchId + "']").attr("selected", "selected");
				}else if (thisCat != null){
					savedBenchId = "0" + thisCat.id;
				}else{
					savedBenchId = "0";
				}
				if (updateBenchFn != undefined){
					var name = getCategoryName(select, thisCat);
					updateBenchFn(savedBenchId, name);
				}
				
				return this;
			},
			hideBench : function(){
				fRootDiv.find(".bottomBar").hide();
				return this;
			}, 
			showBench : function(){
				fRootDiv.find(".bottomBar").show();
				return this;
			},
			clearBench : function(){
				fRootDiv.find(".bottomBar").remove();
				return this;
			},
			getHtml : function(){
				return fRootDiv.find(".chartBottom")
			}
		}
		return footerPublic;
	};
	
	/** FramePublic.MIDDLE START  */
	
	framePublic.Middle = function(){
		var fWidth = "640px";
		var fHeight = "320px";
		var fShortHeight = "240px";

		//chart area
		var makeBox = function(){
			if (fRootDiv.find(".borderChart").length <= 0){
				fRootDiv.append(
					'<div class="borderChart relative"></div>'
				);
			}
		};
		
		makeBox();
		var middlePublic = {
			getBorderChart : function(){
				return fRootDiv.find(".borderChart");
			},
			empty : function(){
				middlePublic.getBorderChart().empty();
			}
		};

		/** FUSION CHART START **/
		var redrawSave = function(extraParams){	//this exists out here because MainChart gets rebuilt by dropdown charts when a new chart is selected
			$.noop();
		};
		middlePublic.MainChart = function(dataCallback){
			var fTriggerCallback = (dataCallback != null);
			var fDataCallback = dataCallback || $.noop;
			var fChartIndex = [];
			var getChartId = function(index){
				if (index == undefined){
					index = "";
				}
				return divId + 'ChartDiv' +  index;
			};
			
			var makeChartDiv = function(chartId){
				var chart = $("<div />")
					.attr("id", chartId)
					.addClass("fusionChart")
				;
				chart.on("rendered", fDataCallback); 
				return chart;
			}
			
			var makeChart = function(chartId){
				//figure out if we're unitialized
				var firstBorderChart = fRootDiv.find(".borderChart .fusionChart");
				if (firstBorderChart.length <= 0){
					fRootDiv.find(".borderChart")
						.append(makeChartDiv(chartId))
					;
				//append if id is new
				}else if (fRootDiv.find(".borderChart #" + chartId).length <= 0){
					fRootDiv.find(".chartBottom").before(
						$('<div/>')
							.addClass("borderChart relative noTopBorder")
							.append(makeChartDiv(chartId))
					);
					fRootDiv.find(".fusionChart").css("height", fShortHeight);
				}

				var lastMarginBottom = fRootDiv.find(".borderChart").last().css("marginBottom");
				fRootDiv.find(".borderChart")
					.css("marginBottom", "0px")
					.last()
					.css("marginBottom", lastMarginBottom)
				;
			};

			
			var saveChartUrl = function(swf, url, index){
				var safeIndex = index || 0;
				fChartIndex[safeIndex] = {
					"swf" : swf,
					"url" : url
				};
			}
			var drawChart = function(swf, url, index){
				var safeIndex = index || 0;
				
				saveChartUrl(swf, url, safeIndex);
				
				redrawSave = function(extraParams){
					var newUrl = new Flurry.UrlParams().buildUrl(url, extraParams);
					drawChart(swf, newUrl, safeIndex);
				};
				var chartId = getChartId(safeIndex);
				makeChart(chartId);
				drawMainChartDimensions(url, chartId, "100%", "100%", swf, fTriggerCallback);
			};
			
			var redrawChart = function(index, extraParams){
				var savedChart = fChartIndex[index];
				var chartId = getChartId(index);
				
				var url = new Flurry.UrlParams().buildUrl(savedChart.url, extraParams);
				var swf = savedChart.swf;
				drawMainChartDimensions(url, chartId, "100%", "100%", swf, fTriggerCallback);
			};
			
			var mainPub = {
				draw : function(swf, url){
					drawChart(swf, url);
					return this;
				},
				updateParams : function(extraParams, index){
					var safeIndex = index || 0;
					var chartData = fChartIndex[safeIndex];
					var oldUrl = chartData.url;
					var newUrl = new Flurry.UrlParams().buildUrl(oldUrl, extraParams); 
					saveChartUrl(chartData.swf, newUrl, safeIndex);
					return this;
				},
				redraw : function(extraParams){
					redrawSave(extraParams);
					
					return this;
				},
				appendChart : function(swf, url, index){
					drawChart(swf, url, index);
					return this;
				},
			};
			
			mainPub.SideBoxes = function(index){
				if (index == undefined){
					index = 0;
				}
				var fBorderChart = fRootDiv.find(".borderChart").eq(index);
				fBorderChart.find(".fusionChart").css("height", fShortHeight);
				redrawChart(index);
				
				fBorderChart.find(".fusionChart").addClass("skinnyChart");
				fBorderChart.prepend('<ul class="skinnyChartNeighbor"></ul>');
				
				var fNeighbor = fBorderChart.find(".skinnyChartNeighbor");
				
				var pub = {
					addName : function(name){
						var nameDiv = fNeighbor.find(".nameDiv"); 
						if (nameDiv.length <= 0){
							fNeighbor.prepend('<li class="nameDiv noBorder"><span></span></li>');
							nameDiv = fNeighbor.find(".nameDiv");
						}
						
						nameDiv.find("span").html(name);
						return this;
					},
					//clazz should be unique to each collection of sideboxes, but not necessarily unique for the whole page
					addSparkBox : function(name, value, clazz){
						if (fNeighbor.find("li."+clazz).length <= 0){
							fNeighbor.append(
								'<li class="' + clazz + '">'
							+		'<span class="key sparkType">'
							+			name
							+		'</span>'
							+		'<span class="sparkNumber">'
							+			value
							+		'</span>'
							+	'</li>'
							);
						}else{
							var li = fNeighbor.find("li."+clazz).first();
							li.find(".sparkType").html(name);
							li.find(".sparkNumber").html(value);
						}
						return this;
					},
					addSparkBoxUrl : function(name, url, clazz){
						pub.addSparkBox(name, "", clazz);
						var clazzStr = "li";
						if (clazz != undefined){
							clazzStr += "." + clazz;
						}
						var li = fNeighbor.find(clazzStr).last();
						var valueField = li.find(".sparkNumber");
						
						YAHOO.util.Connect.asyncRequest('GET', url, {
							success: function(o) 
							{
								if (o.status == 200 && o.responseText != undefined)
								{
									var html = o.responseText;
									valueField.html(html);
									resizeContainers();
								}
							},
							failure: function(o) {/* do nothing */}
						});
						return this;
					},
					addSummaryBox : function(name, percent, actual, clazz){
						if (fNeighbor.find("li."+clazz).length <= 0){
							fNeighbor.append(
								'<li class="' + clazz + '">'
							+		'<span class="sparkNumber">'
							+			'<span class="percentage">'
							+				percent + '%'
							+			'</span>'
							+		'</span>'
							+		'<br/>'
							+		'<span class="sparkNumber weak">'
							+			'<span class="actual">'
							+				actual
							+			'</span>'
							+		'</span>'
							+		'<span class="key sparkType">'
							+			name
							+		'</span>'
							+	'</li>'
							);
						}else{
							var li = fNeighbor.find("li."+clazz).first();
							li.find(".sparkNumber .percentage").html(percent + "%");
							li.find(".sparkNumber.weak .actual").html(actual);
							li.find(".sparkType").html(name);
						}
						return this;
					},
					//does not return this
					getSparkBoxByClass : function(clazz){
						return fNeighbor.find("li."+clazz);
					}
					
				};
				
				return pub;
			};
			
			return mainPub;
		};
		
		/** FUSION CHART END **/
		
		/** SPARK GROUP START **/
		
		middlePublic.SparkGroup = function(){
			var sparkGroupClassName = "sparkHalf"; 
			var getSparkId = function(index){
				return divId + 'SparkDiv' + index;
			};
			var drawSparkCount = function($div, url){
				$div.empty();
				YAHOO.util.Connect.asyncRequest('GET', url, {
					success: function(o) 
					{
						if (o.status == 200 && o.responseText != undefined)
						{
							var html = o.responseText;
							$div
								.html(html)
								.trigger("loaded")
							;
							resizeContainers();
						}
					},
					failure: function(o) {/* do nothing */}
				});
			};
			var makeSpark = function(index){
				var sparkId = getSparkId(index);
				var sparkSearch = fRootDiv.find("#"+sparkId);
				if (sparkSearch.length == 0){
					var $div = makeNextSparkArea();
					$div.append(
						'<div class="spark"> '
					+		'<div id="' + sparkId + '" class="inlineBlock">&nbsp;</div> '
					+	'</div> '
					+	'<div class="sparkCount inlineBlock">&nbsp;</div> '
					);
					return $div;
				}else{
					return sparkSearch.parents("." + sparkGroupClassName);
				}
			};
			var makeNextSparkArea = function(){
				var borderChart = fRootDiv.find(".borderChart");
				var rows = borderChart.find(".centered");
				if (rows.length <= 0){
					rows = makeNewRow(borderChart);
				}

				var lastLeft = rows.last().find(".leftHalf");
				var className = "leftHalf";
				if (lastLeft.length > 0){
					className = "rightHalf";
				}
				var lastRight = rows.last().find(".rightHalf");
				if (lastRight.length > 0){
					className = "leftHalf";
					rows = makeNewRow(borderChart);
				}
				
				var newDiv = $('<div />')
					.addClass(className)
					.addClass(sparkGroupClassName)
				;
				newDiv.appendTo(rows.last());
				return newDiv;
			};
			var makeNewRow = function($div){
				var newRow = $('<div class="centered relative" style="width:640px"></div><div class="clearBoth" style="line-height:0px;height:0px;"></div>');
				$div.append(newRow);
				return $div.find(".centered");
			};
			var pub = {
				draw : function(index, lineUrl, countUrl){
					var sparkArea = makeSpark(index)
						.css({
							"opacity" :"0",
							"visibility" : "hidden"
						})
					;
					
					var sparkId = getSparkId(index);
					drawSparkChart(sparkId, lineUrl);
					
					var sparkCount = sparkArea.find(".sparkCount");
					drawSparkCount(sparkCount, countUrl);
					
					sparkCount.on("loaded", function(e){
						sparkArea
							.css({
								'visibility' : 'visible',
								'opacity' : '1'
							})
						;
					});
				}, 
			};
			return pub;
		};
		
		/** SPARK GROUP END **/
		
		/** FUSION MAP START **/
		
		middlePublic.FusionMap = function(xmlUrl, sdkUrl){
			var fXmlUrl = xmlUrl;
			var fSdkUrl = sdkUrl;
			var getMapId = function(){
				return divId + 'MapChartDiv';
			};
			var getMapNavId = function(){
				return divId + "MapReturnLink";
			};
			var getMapReturn = function(){
				return fRootDiv.find(".borderChart .mapReturn");
			};
			//init
			var initNavUI = function(){
				var borderChart = fRootDiv.find(".borderChart");
				borderChart.find(".mapReturn").remove();
				
				$("<div />")
					.attr("id", getMapNavId())
					.addClass("mapReturn")
					.hide()
					.prependTo(borderChart)
				;
				
				getMapReturn().prepend(
					'<span><a class="colorMediumBlue bold hover">World</a></span>'
				);
				var worldLink = getMapReturn().find("span a");
				makeNavLink(worldLink, "FCMap_World8.swf", 0, 0, "World");
			};
			var initChart = function(){
				initNavUI();
				var borderChart = fRootDiv.find(".borderChart");
				var fusionChart = $("<div />")
									.attr("id", getMapId())
									.addClass("fusionChart")
									.html("&nbsp;")
									.appendTo(borderChart)
								  ;
			};
			
			var getUpsellLightbox = function(){
				var lightboxId = "lightboxID_mapUpsell";
				
				var bodyText = 'You have not yet reported deeper location data. '
				
				var body = $(bodyText);
				if (fSdkUrl != undefined){
					body.append("<br/><br/>");
					
					var sdkLink = $("<a />")
								    .attr("href", fSdkUrl)
								    .attr("class", "colorMediumBlue bold spanHover")
								    .appendTo(body)
								  ;
					var linkSpan = $("<span />")
								     .html("View the SDK instructions to learn how.")
								     .appendTo(sdkLink)
								   ;
				}
				
				var box = new Flurry.Lightbox(lightboxId)
					.setTitle('Geolocation Data Not Available')
					.setBody(body)
				;
				
				return box;
			};
			
			var makeMap = function(swfFile, dataUrl){
				var map = new FusionMaps("/jsp/charts/maps/" + swfFile, randomChartID(), "100%", "100%", "0", "0");
				map.setDataURL(escape(dataUrl));
				map.setTransparent(true);
				map.render(getMapId());
			};
			
			var mapDrillDownSaved = function(){
				var zoneId = readCookie("_map_zoneId");
				var zoomLevel = readCookie("_map_zoomLevel");
				
				var savedGeoJsonUrl = new Flurry.UrlParams("/geoSavedJson.do")
					.addParams({
						"zoneId" : zoneId,
						"zoomLevel" : zoomLevel
					})
					.buildUrl()
				;
				$.getJSON(savedGeoJsonUrl, function(data){
					var navGroup = MapUtils.buildReturnLink(data, fXmlUrl);
					addAttributes(navGroup);
					var mapReturn = getMapReturn()
						.empty()
						.append(navGroup)
					;
					
					if (zoomLevel == 0) {
						mapReturn.hide();
					} else {
						mapReturn.show();
					}
					
					var swfFile = data[0].swf;
					var dataUrl = new Flurry.UrlParams().buildUrl(fXmlUrl, 
						{
							"zoneId" : zoneId,
							"zoomLevel" : zoomLevel,
							"uniqueId" : getMapId()
						}
					);
					
					makeMap(swfFile, dataUrl);
				});
			}

			function setXmlUrl(url){
				fXmlUrl = url;
			}
			
			function addAttributes(navGroup){
				navGroup.find("a").each(function(){
					var link = $(this);
					var swfFile = link.attr("swffile");
					var zoomLevel = link.attr("zoomlevel");
					var zoneId = link.attr("zoneid");
					var zoneName = link.html();
					
					makeNavLink(link, swfFile, zoomLevel, zoneId, zoneName);
				});

			}
			
			function makeNavLink($link, swfFile, zoomLevel, zoneId, zoneName){
				$link.attr("swffile", swfFile)
					.attr("zoomlevel", zoomLevel)
					.attr("zoneid", zoneId)
					.off("click")
					.on("click", function(){
						mapDrillDownCore(swfFile, zoomLevel, zoneId, zoneName);
					})
				;
			};
			
			function mapDrillDownCore(swfFile, zoomLevel, zoneId, zoneName){
				var mapReturn = getMapReturn();
				
				var dataUrl = new Flurry.UrlParams().buildUrl(fXmlUrl, 
					{
						"zoneId" : zoneId,
						"zoomLevel" : zoomLevel,
						"uniqueId" : getMapId()
					}
				);

				if (zoomLevel > 0)
				{
					mapReturn.show();
					var span = document.createElement("span");
					
					var spanArrow = document.createElement("span");
					spanArrow.innerHTML = " &raquo; ";
					spanArrow.className = "colorBlack bold";
					
					var link = document.createElement("a");
					link.className = "colorMediumBlue bold hover";
					link.innerHTML = zoneName;
					makeNavLink($(link), swfFile, zoomLevel, zoneId, zoneName);
					
					span.appendChild(spanArrow);
					span.appendChild(link);
					
					while (mapReturn.children("span").length > zoomLevel)
					{
						mapReturn.children("span").last().remove();
					}
					
					mapReturn.append(span);
				}
				else
				{
					//strip nav down to worldLevel
					mapReturn.hide();
				}
				
				//save these in global cookie
				//zoomLevel
				createCookie("_map_zoomLevel", zoomLevel);
				//zoneId
				createCookie("_map_zoneId", zoneId);

				makeMap(swfFile, dataUrl);
			}
			
			function resetMap(checkCookie){
				var zoomLevel = readCookie("_map_zoomLevel");
				//if we detect saved global cookie
				if (checkCookie && zoomLevel != null)
				{
					mapDrillDownSaved();
				}
				else
				{
					mapDrillDownCore("FCMap_World8.swf", 0, 0, "World");
				}
			};
			
			function mapDrillDown(swfFile, baseUrl, zoomLevel, zoneId, zoneName){
				mapDrillDownCore(swfFile, zoomLevel, zoneId, zoneName)
			};
			
			initChart();
			var pub = {
				draw : function(){
					if (window.lightbox_mapUpsell == undefined){
						window.lightbox_mapUpsell = getUpsellLightbox(fSdkUrl).getYuiBox();
					}
					
					//register these globally - which means we can only have one map on the page at a time
					//which has always been the case
					window.globalMapDrillDowns[getMapId()] = mapDrillDown;
					
					if(FlashDetect != null && FlashDetect.installed){
						resetMap(true);
					}else{
						var noFlashText = $('<div class="centered colorDarkGray" style="vertical-align:middle;font-size:12px;font-weight:bold">Flash is required to render maps.</div>');
						fRootDiv.find(".borderChart .fusionChart")
							.empty()
							.append(noFlashText);
						noFlashText.css("lineHeight", noFlashText.parent().height()+ "px");
					}
				},
				//sets a new data endpoint and redraws the map
				setXmlUrl : function(url){
					setXmlUrl(url);
					pub.draw();
				}
			};
			return pub;
		};
		
		/** FUSION MAPS END **/
		
		/** YUI TABLE START **/
		
		middlePublic.YuiTable = function(paginated, embedded){
			var fPageIndex, //pageNum
				fPageData, 	//cache of pages
				fJsonUrl,	//base url
				fTable		//yui table object
			;
			
			//pagination modes
			var fPaginated = paginated || false;
			var fEmbedded = embedded || false;
			
			var getTableId = function(){
				return divId + 'TableDiv';
			};
			var getTableDiv = function(){
				return fRootDiv.find(".borderChart #" + getTableId())
			};
			
			//clears cached data and resets page count to 0
			var resetTable = function(jsonUrl){
				fPageIndex = 0;
				fPageData = [];
				if (jsonUrl != null && (jsonUrl instanceof String || typeof jsonUrl == "string")){
					fJsonUrl = new Flurry.UrlParams(jsonUrl, true);
				}
				
				getTableDiv().empty().append(
					$("<img />").attr("src", "/images/ui/ajax-loader.gif")
				);
			};
			
			//applies css/yui structure
			var initTableDiv = function(){
				var borderChart = fRootDiv.find(".borderChart");
				borderChart.addClass("yuiTable")
				if (fEmbedded){
					borderChart.addClass("embeddedTable");
				}
				if (getTableDiv().length <= 0){
					borderChart.append(
						'<div id="' + getTableId() + '" style="width:100%; display:inline-block;" class="yui-dt"></div>'
					);
				}
				resetTable(fJsonUrl);
			};
			
			//head/footer ui for controlling pagination, only makes empty shell
			var makePaginationHeader = function(className){
				var head = $('<div class="' + className + ' relative"></div>');
				
				if (fPaginated === "stream"){
					var loadMore = $('<div class="loadMore"></div>')
						.hide()
						.append('<a class="spanHover bold colorMediumBlue"><span>load more results</span></a>')
						.appendTo(head)
					;
				}else if (fPaginated === true){
					var prevDiv = $('<div class="prevDiv absoluteLeft"></div>')
						.hide()
						.append('<a class="spanHover bold colorMediumBlue"><span>&laquo; prev</span></a>')
					;
					
					var pageNum = $('<div class="pagNumDiv bold"></div>');
					
					var nextDiv = $('<div class="nextDiv absoluteRight"></div>')
						.hide()
						.append('<a class="spanHover bold colorMediumBlue"><span>next &raquo;</span></a>')
					;
					
					head
						.append(prevDiv)
						.append(pageNum)
						.append(nextDiv)
					;
				}
				
				return head;
			};
			
			//puts headers around the table
			var makePaginationFrame = function(){

				var tableDiv = getTableDiv();
				if (fPaginated === "stream"){
					var streamHeader = makePaginationHeader("paginationStream");
					tableDiv.after(streamHeader);
				}else{
					if (fRootDiv.find(".paginationTop").length <= 0){
						var pagTop = makePaginationHeader("paginationTop");
						var pagBot = makePaginationHeader("paginationBot");
						
						tableDiv
							.before(pagTop)
							.after(pagBot)
						;
					}
				}
			};
			
			//append default configs to yui table config
			var makeConfigs = function(custom){
				var configs =  {
					"dynamicData" : false
				};
				
				configs = $.extend(configs, custom);
				
				return configs;
			};
			
			//fetch a new page via ajax but insert it into the existing table
			var insertNewPage = function(jsonUrl, tableConfig){
				loadNewPage(jsonUrl, tableConfig, true);
			};
			
			//fetch a new page via ajax, store in cache
			var loadNewPage = function(jsonUrl, tableConfig, insertIntoExistingTable){
				var schema = tableConfig.schema;
				var columnDefs = tableConfig.columnDefs;
				var configs = makeConfigs(tableConfig.config);
				
				var dataSource = new YAHOO.util.LocalDataSource({});
			    dataSource.responseSchema = schema;
				
			    if (fTable == null){
			    	fTable = new YAHOO.widget.DataTable(getTableId(), columnDefs, dataSource, configs);
			    	fRootDiv.find(".yui-dt-message .yui-dt-liner").html("Loading...");
			    	sideNavHeightPrepTable();
			    }
				
				fJsonUrl = new Flurry.UrlParams(jsonUrl, true);
				$.ajax({
					url: jsonUrl,
					dataType: 'json',
					cache: false,
					error: function(){
						if (insertIntoExistingTable){
							fRootDiv.find(".borderChart .loadMore").hide();
						}else{
							fRootDiv.find(".yui-dt-message .yui-dt-liner").html("Error Loading Results.");
						}
					},
					success: function(data){
						var pageNum = data.Response.PageNum || 1;
						var pageIndex = pageNum - 1;
						fPageData[pageIndex] = data;

						if (insertIntoExistingTable){
							insertNewRows(data, tableConfig);
						}else{
							loadCachedPage(pageIndex, tableConfig);
						}

						fRootDiv.trigger("table.dataLoaded", [fPageData]);
					}
				})
			};

			function formatRow(rowData, tableConfig){
				var row = {};
				
				$.each(tableConfig.schema.fields, function(index, field){
					var key = field.key;
					var value = getObjectFromPath(rowData, key);
					
					row[key] = value;
				});
				
				return row;
			}
			function insertNewRows(pageData, tableConfig, firstPass){
				//insert new rows
				if (!firstPass){
					$.each(pageData.Response.Results, function(index, row){
						var yuiRow = formatRow(row, tableConfig);
						fTable.addRow(yuiRow);
					});
					sideNavHeightAdjust();
				}
				
				//update pagination links
				var borderChart = fRootDiv.find(".borderChart");
				var hasNext = pageData.Response.HasNext;

				if (!hasNext){
					//there is no next page
					borderChart.find(".loadMore")
						.hide()
						.heightAdjust()
					;
				}else{
					var nextOffset = pageData.Response.NextOffset
					var sort = pageData.Response.Sort;
					var pageNum = pageData.Response.PageNum || 0;

					borderChart.find(".loadMore")
						.show()
						.off("click")
						.on("click", function(e){
							fJsonUrl.addParams({
								"lastPage" : pageNum,
								"offset" : nextOffset,
								"sort" : sort
							});
							
							var url = fJsonUrl.buildUrl();
							insertNewPage(url, tableConfig);
							
							$(this)
								.hide()
								.heightAdjust()
							;
							
							e.stopPropagation();
							e.preventDefault();
						})
					;
				}

			}
			
			//load a page out of the cache
			function loadCachedPage(pageIndex, tableConfig){
				fPageIndex = pageIndex;
				loadTable(fPageData[pageIndex], tableConfig)
			};
			
			//given json data, load the table
			function loadTable(pageData, tableConfig){
				
				var schema = tableConfig.schema;
				var columnDefs = tableConfig.columnDefs;
				var configs = makeConfigs(tableConfig.config);
				var redrawFunc = tableConfig.redrawFunc || $.noop;
				
				//default sorting
				if (tableConfig.config && tableConfig.config.sortedBy){
					var key = tableConfig.config.sortedBy.key;
					var dir = tableConfig.config.sortedBy.dir;

					var list = getObjectFromPath(pageData, schema.resultsList);

					var fields = schema.fields;
					var parser = getParser(fields, key);
					
					//check parse based on columnDefs
					var columnDefsSize = columnDefs.length;
					for (var i = 0 ; i < columnDefsSize ; i++){
						var column = columnDefs[i];
						if (column.key == key){
							if (column.sortOptions && column.sortOptions.field){
								key = column.sortOptions.field;
								parser = getParser(fields, key);
								break;
							}
							break;
						}
					}
					
					var primer = parseInt;
					var stringPrimer = function(a) {
						return a;
					}
					if (parser == "string"){
						primer = stringPrimer;
					}
					var secondarySortKeys = [];
					var secondarySortDirections = [];
					var secondarySortPrimers = [];
					if(tableConfig.config.secondarySort){
						var secondarySorters = tableConfig.config.secondarySort;
						var secondarySortSize = secondarySorters.length;
						var secondaryKey;
						for (var i = 0; i < secondarySortSize; i++){
							secondaryKey = secondarySorters[i].key
							secondarySortKeys.push(secondaryKey);
							secondarySortDirections.push(secondarySorters[i].dir == 'yui-dt-desc');
							if(getParser(fields, secondaryKey) == "string") {
								secondarySortPrimers.push(stringPrimer);
							} else {
								secondarySortPrimers.push(parseInt);
							}
						}
					}
					list.sort( sortBy(key, (dir == 'yui-dt-desc'), primer, secondarySortKeys,
							secondarySortDirections, secondarySortPrimers));
				}
				
				var dataSource = new YAHOO.util.LocalDataSource(pageData);
			    dataSource.responseSchema = schema;
			 
			    // create data table
			    var dataTable = new YAHOO.widget.DataTable(getTableId(), columnDefs, dataSource, configs);
			    fTable = dataTable;
				sideNavHeightPrepTable();
				
				// Enables row highlighting
				dataTable.subscribe("rowMouseoverEvent", dataTable.onEventHighlightRow);
				dataTable.subscribe("rowMouseoutEvent", dataTable.onEventUnhighlightRow);

				// Handles event when table is drawn
				dataTable.subscribe('renderEvent', function(){
					redrawFunc();
					sideNavHeightPrepTable();
				});
				if (tableConfig.postRenderEvent){
					dataTable.subscribe("postRenderEvent", tableConfig.postRenderEvent);
				}
				if (tableConfig.resizeEvent){
					window.addEventListener('resize', function(){
						(tableConfig.resizeEvent)();
						sideNavHeightPrepTable();
					});
				}

				var pageNum = pageData.Response.PageNum || 0;
				var prevPage = (pageNum - 1);
				var offset = fJsonUrl.getParam("offset") || 0;
				var hasNext = pageData.Response.HasNext;
				var nextOffset = pageData.Response.NextOffset
				var sort = pageData.Response.Sort;

				if (!fPaginated && tableConfig.theadCellClickHandler){
					dataTable.subscribe('theadCellClickEvent', tableConfig.theadCellClickHandler);
				} else if (fPaginated === "stream"){
					if (hasNext){
						insertNewRows(pageData, tableConfig, true);
					}
				} else if (fPaginated === true){
					//pageNum
					if (pageNum > 0){
						//if paginated, need to reset table to page 1 and use the new sorting
						dataTable.subscribe('theadCellClickEvent', 
							function(event){
								cellIndex = event.target.cellIndex;
								
								fJsonUrl.addParams({
									"sort" : cellIndex
								});
								
								var url = fJsonUrl.buildUrl();
								resetTable(fJsonUrl);
								loadNewPage(url, tableConfig);
							}
						);
						
						var borderChart = fRootDiv.find(".borderChart");
						
						borderChart.find(".pagNumDiv").html("Page " + pageNum);
						
						//prev link
						if (prevPage < 1 || offset <= 0){
							//there is no previous page
							borderChart.find(".prevDiv").hide();
						}else{
							borderChart.find(".prevDiv")
								.show()
								.off("click")
								.on("click", function(e){
									loadCachedPage(fPageIndex-1, tableConfig);
									
									e.stopPropagation();
									e.preventDefault();
								})
							;
						}
						
						//next link
						if (!hasNext){
							//there is no next page
							borderChart.find(".nextDiv").hide();

							//hide page counts if we're on page 1 and no possible page 2
							if (pageNum < 2){
								borderChart.find(".pagNumDiv").hide();
							}
						}else{
							borderChart.find(".nextDiv")
								.show()
								.off("click")
								.on("click", function(e){
									//get already loaded next page
									if (pageNum < fPageData.length){
										loadCachedPage(fPageIndex+1, tableConfig);
									//find a new page
									} else {
										fJsonUrl.addParams({
											"lastPage" : pageNum,
											"offset" : nextOffset,
											"sort" : sort
										});
										
										var url = fJsonUrl.buildUrl();
										loadNewPage(url, tableConfig);
									}
									
									e.stopPropagation();
									e.preventDefault();
								})
							;
						}
					}
				}
				return dataTable;
			};

			
			//misc helper functions
			function getObjectFromPath(baseObj, path){
				var split = path.split(".");
				var filtered = baseObj;
				
				var size = split.length;
				for (var i = 0 ; i < size ; i++){
					filtered = filtered[split[i]]; 
				}
				return filtered;
			}
			//http://stackoverflow.com/questions/979256/how-to-sort-an-array-of-javascript-objects
			function sortBy(path, reverse, primer, secondaryPaths, secondaryDirections, secondaryPrimers){
				var key = function(x) {
					var obj = getObjectFromPath(x, path);
					return primer ? primer(obj) : obj; 
				}
				
				var secondaryKey = function(x, index) {
					var obj = getObjectFromPath(x, secondaryPaths[index]);
					if(secondaryPrimers && secondaryPrimers[index]) {
						return secondaryPrimers[index](obj);
					} else {
						return obj;
					}
				}
				
				return (function(a,b){
					var A = key(a), B = key(b);
					var dir = [1,-1][+!!reverse];	//!! sanitizes argument reverse, + to convert to int, then grab index of array
					var comp = (A < B ? -1 : (A > B ? 1 : 0)) * dir;
					if(secondaryPaths) {
						var i = 0;
						while(comp == 0 && i < secondaryPaths.length) {
							A = secondaryKey(a, i);
							B = secondaryKey(b, i);
							if(secondaryDirections && typeof(secondaryDirections[i]) !== "undefined"){
								dir = [1,-1][+!!secondaryDirections[i]];
							}
							comp = (A < B ? -1 : (A > B ? 1 : 0)) * dir;
							i += 1;
						}
					}
					return comp;
				});
			}
			function getParser(fields, key){
				var fieldsSize = fields.length;
				var parser = "number";
				//check parser based on schema fields 
				for (var i = 0 ; i < fieldsSize ; i++){
					if (fields[i].key == key){
						parser = fields[i].parser;
						break;
					}
				}
				return parser;
			}
			
			initTableDiv();
			var pub = {
				draw : function(jsonUrl, tableConfig){
					if (fPaginated){
						makePaginationFrame();
					}
					
					loadNewPage(jsonUrl, tableConfig);
					return this;
				},
				reset : function(jsonUrl, tableConfig){
					resetTable(jsonUrl);
					pub.draw(jsonUrl, tableConfig);
					return this;
				}
			};
			
			return pub;
		};
		
		/** YUI TABLE END **/
		
		/** ZOOM START **/
		
		/**
		 * config : {
		 * 		containerClass : "name of class used to id the container div", default: chartLinks,
		 *		prefix : "text to show ahead of links", default : ""
		 * }
		 */
		var ChartLinks = function(config) {

			var _defaults = {
				containerClass : "chartLinks",
				prefix : ""
			};
			var _config = $.extend(true, _defaults, config); //recursive copy

			var _linksHolder = null;
			var _container = null;
			var _linksMap = {};
			
			(function init(){
				var borderChart = fRootDiv.find(".borderChart");
				
				_container = borderChart.find("." + _config.containerClass.replace(/ /g, '.'));	//replace spaces for periods here to use css selectors
				
				var linkHolderClass = "linkHolder";
				
				if (_container.length <= 0){
					_container = $("<div />")
						.addClass("absoluteRightMargined colorBlack")
						.addClass(_config.containerClass)
					;
					
					setupContainer(_config);
					
					borderChart
						.addClass("relative")
						.prepend(_container)
					;
				}else{
					//already created, just need to reinitialize
					_container.empty();
					setupContainer(_config);
				}
				function setupContainer(config){
					if (config.prefix && config.prefix.length > 0){
						_container
							.append(config.prefix)
							.append("&nbsp;")
						;
					}
					_linksHolder = $("<div />")
						.addClass("inlineBlock")
						.addClass(linkHolderClass);
					;
					
					_container
						.append(_linksHolder)
					;
				}
			}());
			
			function makeSeparator(){
				return $("<span/>")
					.addClass("linksSeparator")
					.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
				;
			}
			
			function makeLink(content){
				return $("<a />")
					.addClass("colorBlack hover")
					.append(content)
					.off("click.bold")
					.on("click.bold", function(){
						$(this).parent().find("a").removeClass("bold activeLink");
						$(this)
							.addClass("bold activeLink")
						;
					})
				;
			}
			
			var linksPub = {
				/**
				 * optConfig : {
				 * 		separator : tail element after a link,
				 * 		id : cssClass that can also be used to pull the link from a map, if not set, will not be put into map
				 * 			//you should REALLY specify an id
				 * }
				 */
				addLink : function(linkContent, optIndex, optConfig){
					var newLink = makeLink(linkContent);
					if (optConfig.id){
						var name = optConfig.id;
						_linksMap[name] = newLink;
						newLink
							.addClass(name)
							.attr('key', name)
						;
					}
					
					//insert at optional position and handle separator
					var separator = optConfig.separator || makeSeparator();
					_linksHolder.insertAt(newLink, optIndex, {
						trailObject : separator,
						childSelector : "a"
					});
					
					//return the link in case we need to do things like add more classes
					return newLink;
				}, 
				addToFront : function(linkContent, optConfig){
					return this.addLink(linkContent, 0, optConfig);
				},
				addToBack : function(linkContent, optConfig){
					return this.addLink(linkContent, -1, optConfig);
				},
				clear : function(){
					_linksHolder.html("");
					return this;
				},
				hide : function(){
					_container.hide().heightAdjust();
					return this;
				},
				show : function(){
					_container.show().heightAdjust();
					return this;
				},
				//doesn't return this
				get : function(key){
					return _linksMap[key];
				},
				getFirst : function(){
					return this.get(this.links().first().attr("key"));
				},
				links : function(){
					return _linksHolder.find("a");
				},
				getContainer : function(){
					return _container;
				},
				getLinksMap : function(){
					return _linksMap;
				},
				join : function(otherChartLinks){
					var selfZooms = this.getLinksMap();
					var otherZooms = otherChartLinks.getLinksMap();
					
					//match the links if same key
					$.each(selfZooms, function(key, selfZoom){
						var otherZoom = otherZooms[key];
						
						if (otherZoom){
							var sz = $.maker(selfZoom);
							var oz = $.maker(otherZoom);
							
							link(sz, oz);
							link(oz, sz);
						}
					});
					
					function link(z1, z2){
						z1.on("click.linker", function(e){
							if (!z2.hasClass("activeLink")){
								z2.trigger("click");
							}
						});
					}
				}
			};
			return linksPub;
		};

		middlePublic.ChartSwitcher = function(){
			var chartLinks = new ChartLinks({
				containerClass : "zoomDiv chartSwitcher",
				prefix: ""
			});
			
			var csPub = {
				addChartLink : function(imgConfig, clickHandler, type){
					var image = $("<img />")
						.css("cursor", "pointer")
						.attr("src", imgConfig.src)
						.attr("title", imgConfig.title)
					;
					
					return chartLinks.addToBack(image, {
							id : type,
							separator : "&nbsp;&nbsp;&nbsp;"
						})
						.on("click.chart", function(){
							Flurry.ChartTypes.savedPieType(type);
							clickHandler();
						})
					;
				},
				addPie : function(clickHandler){
					return this.addChartLink({
						src : "/images/icons/chart_pie.png",
						title : "View as Pie Chart"
					}, clickHandler, "pie");
				},
				addHist : function(clickHandler){
					return this.addChartLink({
						src : "/images/icons/chart_bar.png",
						title : "View as Histogram"
					}, clickHandler, "hist");
				},
				addHistPercent : function(clickHandler){
					return this.addChartLink({
						src : "/images/icons/chart_bar_percent.png",
						title : "View as Histogram By Percentages"
					}, clickHandler, "histPercent");
				},
				addLine : function(clickHandler){
					return this.addChartLink({
						src : "/images/icons/chart_line.png",
						title : "View as Line Chart"
					}, clickHandler, "line");
				},
				addChartTypes : function(chartTypes){
					if (chartTypes){
						$.each(chartTypes, function(type, clickHandler){
							if (type == "pie") {
								csPub.addPie(clickHandler);
							} else if (type == "hist") {
								csPub.addHist(clickHandler);
							} else if (type == "histPercent") {
								csPub.addHistPercent(clickHandler);
							} else if (type == "line") {
								csPub.addLine(clickHandler);
							}
						});
					}
					return this;
				},
				switchType : function(type){
					var typeLink = this.get(type);
					if (typeLink == null){
						typeLink = this.getFirst();
					}
					typeLink.click();
					return this;
				}
			};
			return $.extend({}, chartLinks, csPub);
		};
		
		/**
		 * Creates divs for use with select2 to create autocomplete multi-selects.
		 * Able to add buttons to control that select funcitonality.
		 */
		var ChartSelects = function(config){
			var _defaults = {
				containerClass : "chartSelects"
			}
			
			var _config = $.extend(true, _defaults, config);
			
			var _selectsHolder = null;
			var _container = null;
			var _selectsMap = {};
			
			(function init(){
				var borderChart = fRootDiv.find(".borderChart");
				
				_container = borderChart.find("." + _config.containerClass.replace(/ /g, '.'));	//replace spaces for periods here to use css selectors
				
				var selectHolderClass = "selectHolder";
				
				if (_container.length <= 0){
					_container = $("<div />")
						.addClass("absoluteLeftMargined colorBlack")
						.addClass(_config.containerClass)
					;
					
					setupContainer(_config);
					
					borderChart
						.addClass("relative")
						.prepend(_container)
					;
				}else{
					//already created, just need to reinitialize
					_container.empty();
					setupContainer(_config);
				}
				function setupContainer(config){
					_selectsHolder = $("<div />")
						.addClass("inlineBlock")
						.addClass(selectHolderClass)
						.appendTo(_container)
					;
						
					//Array of arrays
					if (config.selects && config.selects.length > 0){
						var selectsPerRow = config.selectsPerRow || 1;
						var numRows = Math.ceil(config.selects.length / selectsPerRow);
						
						for (var i = 0; i < numRows; i++){
							var $selectRow = $("<div />")
								.addClass("selectHolderRow")
								.appendTo(_selectsHolder)
							;
							for (var j = 0; j < selectsPerRow; j++){
								var $selectArea = $("<div />")
									.addClass("selectContainer")
									.appendTo($selectRow)
								;
								var index = j + i * selectsPerRow;
								var selectData = config.selects[index];
								var id = selectData.id || index;
								var divId = "select" + id;
								var mappedData = {};
								
								if (selectData.prefix){
									var $selectPrefix = $("<span />")
										.addClass("selectPrefix")
										.html(selectData.prefix)
										.appendTo($selectArea)
									;
									mappedData.prefix = $selectPrefix;
								}
								
								var $select;

								//Creates a standard select with options
								if (selectData.options && selectData.options.length > 0){
									$select = $("<select />")
										.attr("id", divId)
										.appendTo($selectArea)
									;
									$.each(selectData.options, function (index, option){
										var $option = $("<option />")
											.html(option.name)
											.attr("value", option.id)
											.appendTo($select)
										;
									})
								} else {
								//Creates a div to be styled by on page js to use select2
									$select = $("<div />")
										.css({
											"display" : "inline-block",
											"width" : "190px",
										})
										.attr("id", divId)
										.appendTo($selectArea)
									;
									
								}
								mappedData.select = $select;
								
								_selectsMap[id] = mappedData;
							}
						}
					}
					
					var buttonsContainer;

					function makeButtonsContainer(){
						if (buttonsContainer === undefined){
							buttonsContainer = $("<div />")
								.addClass("inlineBlock")
								.appendTo(_container)
						}
					}
					//Used to apply the data in the select fields
					if (config.button){
						makeButtonsContainer();
						var clickFunc = config.button.clickFunction || $.noop;
						var name = config.button.name || "Button";
						var $button = $("<button />")
							.on("click", clickFunc)
							.attr("id", "clickButton")
							.addClass("selectButton")
							.html(name)
							.appendTo(buttonsContainer)
						;
					}
					
					//Used to clear the all the data in the select fields
					if (config.clearButton){
						makeButtonsContainer();
						var clearFunc = config.clearButton.clearFunc || $.noop;						
						var name = config.clearButton.name || "Button";
						var $clearButton = $("<button />")
							.on("click", clearFunc)
							.attr("id", "clearButton")
							.addClass("selectButton")
							.html(name)
							.appendTo(buttonsContainer)
						;
					}
					//Sets up both buttons to be the same size
					if (config.buttonWidth){
						buttonsContainer.css({
							"width" : config.buttonWidth,
						});
						if ($button != undefined){
							$button.width(config.buttonWidth);
						}
						if ($clearButton != undefined){
							$clearButton.width(config.buttonWidth);
						}
					}
				}
			}());
			
			var selectsPub = {
					clear : function(){
						_selectsHolder.html("");
						return this;
					},
					hide : function(){
						_container.hide().heightAdjust();
						return this;
					},
					show : function(){
						_container.show().heightAdjust();
						return this;
					},
					get : function(key){
						return _selectsMap[key];
					},
					getContainer : function(){
						return _container;
					},
					getMap : function(){
						return _selectsMap;
					},
			};
			
			return selectsPub;
		}
		
		//TODO: Either dry up geo zoom code and time zoom code with a common
		//      base zoom class, or create geo zoom as an extension
		middlePublic.Zoom = function(){
			var chartLinks = new ChartLinks({
				containerClass : "zoomDiv",
				prefix : "Zoom: "
			});
			var zoomPub = {
				appendBasicZooms : function(altDailyLightbox){
					//day link
					var daysZoom = chartLinks.addToBack("days", {id : "daysZoom"});
					if (altDailyLightbox != null){
						daysZoom
							.off("click.lightbox")
							.off("click.bold")
							.on("click.lightbox", function(){
								altLightbox.toggle();
							})
						;
					}
					//week link
					chartLinks.addToBack("weeks", {id : "weeksZoom"});
					//month link
					chartLinks.addToBack("months", {id : "monthsZoom"});
					
					return this;
				},
				prependHourlyZoom : function(altLightbox){
					//hours link
					var hoursZoom = chartLinks.addToFront("hours", {id : "hoursZoom"});
					
					if (altLightbox != null){
						hoursZoom
							.off("click.lightbox")
							.off("click.bold")
							.on("click.lightbox", function(){
								altLightbox.toggle();
							})
						;
					}
					
					return this;
				},
				prependDayPartingZoom : function(altLightbox){
					//time Of Day link
					var timeOfDayZoom = chartLinks.addLink("time of day", 0, {
						id : "timeOfDayZoom", 
						separator: ' |&nbsp;&nbsp; '
					});
					if (altLightbox != null){
						timeOfDayZoom
							.off("click.lightbox")
							.off("click.bold")
							.on("click.lightbox", function(){
								altLightbox.toggle();
							})
						;
					}

					//explain bubble
					var explainLink = $("<a />")
						.click(function(e){
							e.stopPropagation();
							e.preventDefault();

							showExplanation('analytics', 'timeOfDay');
						})
						.addClass('imgHover colorMediumBlue')
						.attr('title', 'Explain Time of Day')
						.css({
							'padding-left' : '3px'
						})
						.insertAfter(timeOfDayZoom)
					;
					var explainImg = $("<img />")
						.attr('src', '/images/icons/help.gif')
						.appendTo(explainLink)
					;
					
					return this;
				},
				addZooms : function(zoomModes){
					if (zoomModes){
						if (zoomModes.basic){
							this.appendBasicZooms(zoomModes.dailyBox);
						}
						if (zoomModes.hourly){
							this.prependHourlyZoom(zoomModes.hourlyBox);
						}
						if (zoomModes.dayparting){
							this.prependDayPartingZoom(zoomModes.daypartingBox);
						}
					}
					return this;
				},
				clear : function(){
					chartLinks.clear();
					return this;
				},
				hide : function(){
					chartLinks.hide();
					return this;
				},
				show : function(){
					chartLinks.show();
					return this;
				},
				addClicks : function(updateChart){
					var links = chartLinks.links();
					var zoomCodeKey = "zoomCode";
					
					links.each(
						function(){
							var zoomParams = zoomPub.ZoomParams;
							//for each zoom type, store its zoomCode
							var link = $(this);
							$.each(zoomParams, function(key, value){
								if (link.hasClass(key)){
									//get the zoom value
									link.data(zoomCodeKey, value);
								}
							});
							//attach click handler which looks at zoomCode
							
							var shouldHandleClick = true;
							$.each(link.data("events"), function(index, events){
								$.each(events, function(index2, event){
									if (event.namespace == "lightbox"){	//if we have a lightbox from above to indicate an error state, don't draw the chart
										shouldHandleClick = false;
										return false;	//works like a break for $.each
									}
								});
							});
							if (shouldHandleClick){
								link
									.off("click.chart")
									.on("click.chart", function(){
										var zoom = $(this).data(zoomCodeKey)
										updateChart(zoom);
									})
									.off("click.update")
									.on("click.update", function(){
										var zoom = $(this).data(zoomCodeKey)
										chartLinks.getContainer().trigger("zoomUpdate", [zoom]);
									})
								;
							}
						}
					);
					
					return this;
				},
				clickZoom : function(zoom){
					$.each(zoomPub.ZoomParams, function(key, value){
						if (value == zoom){
							var link = chartLinks.get(key);
							if (link){
								link.trigger("click");
							}
							return this;
						}
					});
					return this;	
				},
				joinZoom : function(otherZoom){
					chartLinks.join(otherZoom.getChartLinks());
					return this;
				},
				getChartLinks : function(){
					return chartLinks;
				}
			};
			zoomPub.ZoomParams = function(){
				return {
					daysZoom : "d",
					weeksZoom : "w",
					monthsZoom : "m",
					hoursZoom : "h",
					timeOfDayZoom : "dp"
				}
			}();
			
			return zoomPub;
		};
		middlePublic.GeoZoom = function(){
			var chartLinks = new ChartLinks({
				containerClass : "geoZoomDiv",
				prefix : "Geo Zoom: "
			});
			var chartSelects = new ChartSelects({}); //uninitialized
			
			var geoZoomPub = {
				addGeoZooms : function(){
					//region link
					chartLinks.addToBack("Region", {id : "regionZoom"});
					//week link
					chartLinks.addToBack("Country", {id : "countryZoom"});
					//month link
					chartLinks.addToBack("State", {id : "geoAreaZoom"});
					//city link
					chartLinks.addToBack("City", {id : "cityZoom"});
					
					return this;
				},
				addGeoSelects : function(selectConfig){
					chartSelects = new ChartSelects(selectConfig);
					
					return this;
				},
				clear : function(){
					chartLinks.clear();
					chartSelects.clear();
					return this;
				},
				hide : function(){
					chartLinks.hide();
					chartSelects.hide();
					return this;
				},
				show : function(){
					chartLinks.show();
					chartSelects.show();
					return this;
				},
				addClicks : function(updateChart){
					var links = chartLinks.links();
					var zoomCodeKey = "zoomCode";
					
					links.each(
						function(){
							var zoomParams = geoZoomPub.ZoomParams;
							//for each zoom type, store its zoomCode
							var link = $(this);
							$.each(zoomParams, function(key, value){
								if (link.hasClass(key)){
									//get the zoom value
									link.data(zoomCodeKey, value);
								}
							});
							//attach click handler which looks at zoomCode
							
							link
								.off("click.chart")
								.on("click.chart", function(){
									var zoom = $(this).data(zoomCodeKey)
									updateChart(zoom);
								})
								.off("click.update")
								.on("click.update", function(){
									var zoom = $(this).data(zoomCodeKey)
									chartLinks.getContainer().trigger("geoZoomUpdate", [zoom]);
								})
							;
						}
					);
					
					return this;
				},
				clickZoom : function(zoom){
					$.each(geoZoomPub.ZoomParams, function(key, value){
						if (value == zoom){
							var link = chartLinks.get(key);
							if (link){
								link.trigger("click");
							}
							return this;
						}
					});
					return this;	
				},
				joinZoom : function(otherZoom){
					chartLinks.join(otherZoom.getChartLinks());
					return this;
				},
				getChartLinks : function(){
					return chartLinks;
				},
				getChartSelects : function(){
					return chartSelects;
				}
			};
			geoZoomPub.ZoomParams = function(){
				return {
					regionZoom : 0,
					countryZoom : 1,
					geoAreaZoom : 2,
					cityZoom : 3
				}
			}();
			
			return geoZoomPub;
		};		
		/** ZOOM END **/
		
		return middlePublic;
	};
	/** FramePublic.MIDDLE END  */
	
	return framePublic;
};

Flurry.ChartHelpers = Flurry.ChartHelpers || {
	makeHalfChart : function($div){
		var root = $div;
		var leftRight = null;

		var halfContainerCss = "halfContainer";
		var halfCss = "half";
		
		if (root.find("." + halfContainerCss + " " + "." + halfCss).length % 2 == 0){
			var halfContainer = $('<div />')
				.addClass(halfContainerCss)
				.appendTo(root)
			;
			var left = $('<div />')
				.addClass("left")
				.appendTo(halfContainer)
			;
			leftRight = left;
		}else{
			var halfContainer = root.find("." + halfContainerCss).last();
			var right = $('<div />')
				.addClass("right")
				.appendTo(halfContainer)
			;
			leftRight = right;
		}
		var half = $('<div />')
			.addClass(halfCss)
			.appendTo(leftRight)
		;

		return half;
	},
	
	cloneConfigArray : function(configArr){
		function isArray(o) {
			return Object.prototype.toString.call(o) === '[object Array]';
		}
		var isArray = isArray(configArr);
		var configs = configArr;
		if (!isArray){
			configs = [configArr];
		}
		
		var newArray = [];
		$.each(configs, function(index, config){
			var newConfig = {
				"chartUrls" : config.chartUrls.clone()
			};
			newArray.push(newConfig);
		});
		
		if (isArray){
			return newArray;
		}else{
			return newArray[0];
		}
	}
};
//Flurry.DataUrls dataUrls
//Flurry.UrlParams urlParams
Flurry.ChartModule = Flurry.ChartModule || function(divId, urlParams){
	/* private variables */
	var fRootDiv = $("#" + divId);
	fRootDiv.addClass("chartArea");
	
	var fFrame = new Flurry.ChartFrame(divId);
	var fMiddle = new fFrame.Middle();
	var fZoom = new fMiddle.Zoom().hide();
	var fFooter = new fFrame.Footer();
	var fTitle, fTitleLinks;
	
	var fUrlParams = urlParams;
	if (fUrlParams == null){
		fUrlParams = new Flurry.UrlParams();
	}
	
	//Flurry.*Title title
	var makeSingleTitle = function(config){
		var dataUrls = config.chartUrls;
		var extraParams = config.extraParams;

		var zoomModes = config.zoomModes;
		if (dataUrls.spark() != undefined && zoomModes != null && zoomModes.defaultZoom != "dp"){
			var zooms = {
				dontSaveZoom : true,
				zoom : zoomModes.defaultZoom
			};
			fTitle.addSpark(fUrlParams.buildUrl(dataUrls.spark(), $.extend({},extraParams,zooms)), dataUrls.name());
		}else{
			if (dataUrls.count() != undefined){
				fTitle.addCount(dataUrls.count());
			}
			fTitle.addName(dataUrls.name());
		}
	}
	//titleLinks created from Flurry.*Title.makeTitleLinks()
	var addTitleLinks =  function(config, toggleFunction){
		var dataUrls = config.chartUrls;
		var extraParams = config.extraParams || {};
		fTitleLinks.clear();
		if (toggleFunction){
			fTitleLinks.addToggle(toggleFunction);
		}
		if (dataUrls.explain() != undefined){
			fTitleLinks.addExplain(dataUrls.explain());
		}
		if (dataUrls.csv() != undefined){
			fTitleLinks.addCsv(fUrlParams.buildUrl(dataUrls.csv(), extraParams));
		}
		if (dataUrls.report() != undefined){
			fTitleLinks.addReport(fUrlParams.buildUrl(dataUrls.report(), extraParams));
		}
	};
	
	var getChartUrl = function(dataUrls, extraParams){
		return fUrlParams.buildUrl(dataUrls.data(), extraParams);
	};
	
	var logZoomChange = function(z){
		var zoomEventParams = $.extend(true,
			FlurryAgentUtil.buildFlurryAgentProjectParams(),
			{ "zoomLevel" : z }
		);
		FlurryAgentUtil.logEvent("Zoom_Changed", zoomEventParams);
	}
	
	var logGeoZoomChange = function(z){
		var geoZoomEventParams = $.extend(true,
			FlurryAgentUtil.buildFlurryAgentProjectParams(),
			{ "geoZoomLevel" : z }
		);
		FlurryAgentUtil.logEvent("GeoZoom_Changed", geoZoomEventParams);
	}
	
	var makeZooms = function(zoomModes, clickHandler, zoomUpdate){
		var zoomUpdateHandler = zoomUpdate || $.noop;//todo: remove this
		
		if (fZoom.savedZoom == undefined){
			fZoom.savedZoom = zoomModes.defaultZoom;
		}
		fZoom = new fMiddle.Zoom()
			.show()
			.clear()
			.addZooms(zoomModes)
			.addClicks(function(z){
				if (fZoom.savedZoom != z){
					logZoomChange(z);
					zoomUpdateHandler(z); 
				}
				clickHandler(z);
				fZoom.savedZoom = z;
			})
			.clickZoom(fZoom.savedZoom)
		;
	};
	
	var makeGeoZooms = function(geoZoomModes, clickHandler){
		if (fZoom.savedGeoZoom == undefined){
			fZoom.savedGeoZoom = geoZoomModes.savedGeoZoom || 0;
		}
		fZoom = new fMiddle.GeoZoom()
			.show()
			.clear()
			.addGeoSelects(geoZoomModes.selectsConfig)
			.addGeoZooms()
			.addClicks(function(z){
				if (fZoom.savedGeoZoom != z){
					logGeoZoomChange(z);
					//TODO: geo zoom update handler, to be done with an 'onGeoZoomUpdate'
				}
				clickHandler(z);
				fZoom.savedGeoZoom = z;
			})
			.clickZoom(fZoom.savedGeoZoom)
		;
	}
	
	var makeChartTypes = function(configMap, toggleFunction){
		var defaultKeyName = "defaultChartType"; 
		
		var savedChartType = fMiddle.savedChartType || configMap[defaultKeyName];
		
		var chartTypesMap = {};
		
		$.each(configMap, function(type, config){
			if (type != defaultKeyName){
				chartTypesMap[type] = function(){
					fMiddle.savedChartObj = drawNewChart(config, toggleFunction);
					fMiddle.savedChartType = type;
				};
			}
		});
		
		fZoom = new fMiddle.ChartSwitcher()
			.show()
			.clear()
			.addChartTypes(chartTypesMap)
			.switchType(savedChartType)
		;
		
		function drawNewChart(config, toggleFunction){
			//config setup
			var dataUrls = config.chartUrls;
			var extraParams = config.extraParams;
			var dataCallback = config.dataCallback;
			
			//will clear each time
			if (fTitle.isSingleTitle()){
				makeSingleTitle(config);
			}
			addTitleLinks(config, toggleFunction);
			
			var chartUrl = getChartUrl(dataUrls, extraParams);
			var chartSwf = dataUrls.swf();
			
			var mainChart = new fMiddle.MainChart(dataCallback);
			mainChart.draw(chartSwf, chartUrl);
			
			return mainChart;
		}
	};
	
	var drawFusionChart = function(config, zoomUpdatesTitle){
		var dataUrls = config.chartUrls;
		var extraParams = config.extraParams;
		var zoomModes = config.zoomModes;
		var chartTypes = config.chartTypes;
		var benchMode = config.benchMode;
		var dataCallback = config.dataCallback;
		var onZoomUpdate = config.onZoomUpdate || $.noop;
		
		var chartSwf = dataUrls.swf();
		var mainChart = new fMiddle.MainChart(dataCallback);
		
		var chartDrawn = false;
		if (benchMode != null){
			fFooter.showBench()
				  .clearBench()
				  .addBenchmarkExplain();
			if (benchMode.normal != null){
				if (benchMode.normal.checkbox == undefined){
					var cat = benchMode.normal.cat;
					var categories = benchMode.normal.categories;
					//todo: seems like there's a reason why we can't save the bench state, should refactor to redraw fewer times
					//probably a system where we save a params map internally
					//ui control callback updates map and redraws if init finished
					//finish init by passing all the $uis
					fFooter.addBenchmarkNormal(cat, categories, function(newBenchId, newBenchName){
						fUrlParams.addParam("benchmarkID", newBenchId);
						var benchUrl = getChartUrl(dataUrls, extraParams);
						mainChart.draw(chartSwf, benchUrl);
						fUrlParams.removeParam("benchmarkID");
						chartDrawn = true;
						fFooter.savedBenchId = newBenchId;		//need to save these externally so we can keep state between different kinds of charts
					}, fFooter.savedBenchId);
				} else {
					var start = benchMode.normal.checkbox.start;
					var text = benchMode.normal.checkbox.name;
					var param = benchMode.normal.checkbox.param;
					fFooter.addBenchmarkCheckbox(start, text, function(checked){
						fUrlParams.addParam(param, checked);
						var benchUrl = getChartUrl(dataUrls, extraParams);
						mainChart.draw(chartSwf, benchUrl);
						fUrlParams.removeParam(param);
						chartDrawn = true;
						fFooter.savedParam = param;
						fFooter.savedChecked = checked;
					});
				}
			} else if (benchMode.excluded != null){
				fFooter.addBenchmarkExcluded();
			} else if (benchMode.appStore != null){
				var b = benchMode.appStore;
				fFooter.addAppStoreRequest(
					b.appStoreName, 
					b.explanationKey, 
					b.projectId, 
					b.savedAppStoreId
				);
			}
		}else{
			fFooter.hideBench();
		}
		
		//zoom
		if (zoomModes != undefined){
			var clickHandler = function (z){
				//add the zoom value to the central saved params
				fUrlParams.addParam("zoom", z);
				if (benchMode != null && benchMode.normal != null){
					//hide bench if zoom is hourly
					if (z == "h"){
						fFooter.hideBench();
						fUrlParams.removeParam("benchmarkID");
					}else{
						fFooter.showBench();
						if (benchMode.normal.checkbox == undefined){
							fUrlParams.addParam("benchmarkID", fFooter.savedBenchId);
						} else {
							fUrlParams.addParam(fFooter.savedParam, fFooter.savedChecked);
						}
					}
				}
				if (z == "dp"){
					chartSwf = "ScrollCombiDY2D.swf";
				}else{
					chartSwf = dataUrls.swf();
				}
				
				//redraw chart
				var zoomUrl = getChartUrl(dataUrls, extraParams);
				mainChart.draw(chartSwf, zoomUrl);
				//update links in title right
				fTitleLinks.updateParam("zoom", z);
				//possibly update title data
				if (dataUrls.spark() != undefined && zoomUpdatesTitle){
					if (z == "dp"){
						fTitle.addName(dataUrls.name(), true);
					}else{
						var sparkUrl = fUrlParams.buildUrl(dataUrls.spark(), extraParams);
						sparkUrl = updateUrlParam(sparkUrl, "benchmarkID", 0);
						fTitle.addSpark(sparkUrl, dataUrls.name());
					}
				}
			};
			
			var zoomUpdateHandler = function (z){
				onZoomUpdate(z);
			}
			
			makeZooms(zoomModes, clickHandler, zoomUpdateHandler);
			chartDrawn = true;
			
		//chartType
		} else if (chartTypes != undefined) {
			makeChartTypes(chartTypes);
			chartDrawn = true;
		}else{
			fZoom.hide();
			fUrlParams.removeParam("zoom");
			chartDrawn = false;
		}
		
		//draw chart
		if (!chartDrawn) {
			var chartUrl = getChartUrl(dataUrls, extraParams);
			mainChart.draw(chartSwf, chartUrl);
		}
		
		return mainChart;
	};
	
	var addSideBoxes = function(sideBoxes, data, extraParams, type){
		if (data.name != null && data.name != ""){
			sideBoxes.addName(data.name);
		}
		if (data.spark != null){
			var spark = data.spark;
			
			if (spark.value != undefined){
				sideBoxes.addSparkBox(spark.name, spark.value, type);
			} else {
				sideBoxes.addSparkBoxUrl(spark.name, fUrlParams.buildUrl(spark.url, extraParams), type);
			}
		}
		if (data.summary != null && data.summary.length > 0) {
			var index = 0;
			$.each(data.summary, function(){
				sideBoxes.addSummaryBox(this.name, this.percent, this.actual, divId + "SummaryBox" + index);
				index++;
			});
		}
	};
	
	//this should be overridden by each chart type
	var redrawFunc = function(extraParams){
		$.noop();
	};
	var chartModulePublic = {
		redraw : function(extraParams){
			redrawFunc(extraParams);
		},
		makeSingleChart : function(config, zoomUpdatesTitle){
			fTitle = new fFrame.SingleTitle();
			fTitleLinks = fTitle.makeTitleLinks();
			
			makeSingleTitle(config);
			addTitleLinks(config);
			var chartObj = drawFusionChart(config, zoomUpdatesTitle);
			redrawFunc = function(extraParams){
				chartObj.redraw(extraParams);
			};

			return this;
		},
		
		/**
		 * configMap : {
		 * 		an map of normal chart configs, keyed by a string which determines chart type
		 * 		acceptable key values are : [pie], [hist], [histPercent], [line]
		 * }
		 */
		makeMultiTypeChart : function(configMap, startType, toggleFunction){
			//title setup
			fTitle = new fFrame.SingleTitle();
			fTitleLinks = fTitle.makeTitleLinks();

			//no footer support yet
			fFooter.hideBench();
			
			makeChartTypes($.extend(true, {}, configMap, {defaultChartType : startType}), toggleFunction);
			redrawFunc = function(extraParams){
				//savedChartObj created by makeChartTypes
				fMiddle.savedChartObj.redraw(extraParams);
			};
			
			return this;
		},
		
		makeDropDownChart : function(configArr, startIndex, ignoreChartLoad){
			fTitle = new fFrame.DropDownTitle();
			fTitleLinks = fTitle.makeTitleLinks();
			
			$.each(configArr, function(index, config){
				var dataUrls = config.chartUrls;
				var extraParams = config.extraParams;
				//function used to handle matters when different dropdown is selected
				var clickFunction = config.clickFunction || $.noop; 

				var chartUrl = getChartUrl(dataUrls, extraParams);
				var chartSwf = dataUrls.swf();
				
				var name = dataUrls.name();
				//on chart load
				fTitle.addChart(name, function(moreParams){
					var params = moreParams || {};
					config.extraParams = $.extend({}, extraParams, params);
					//will clear title each time
					addTitleLinks(config);
					//draw chart
					var chartObj = drawFusionChart(config, false);
					redrawFunc = function(extraParams){
						chartObj.redraw(extraParams);
					};
					clickFunction(extraParams);
					
					fTitle.hide();
				});
				
			});
			if (!ignoreChartLoad){
				fTitle.loadChart(startIndex);
			}
			
			return this;
		},
		
		makeDropDownTable : function(configArr, startIndex, getConfigFunction, ignoreLoad){
			fTitle = new fFrame.DropDownTitle();
			fTitleLinks = fTitle.makeTitleLinks();
			
			$.each(configArr, function(index, config){
				var dataUrls = config.chartUrls;
				var extraParams = config.extraParams;
				
				var yuiTableConfig = getConfigFunction(index);

				var name = dataUrls.name();
				//on table load
				fTitle.addChart(name, function(moreParams){
					var params = moreParams || {};
					config.extraParams = $.extend({}, extraParams, params);
					
					//will clear title each time
					addTitleLinks(config);
					
					//draw table
					var table = new fMiddle.YuiTable(yuiTableConfig.paginated, yuiTableConfig.embedded);
					var jsonUrl = fUrlParams.buildUrl(dataUrls.data(), extraParams);
					var zoomModes = config.zoomModes;

					//zoom
					if (zoomModes != undefined){
						makeZooms(zoomModes, function(z){
							//add the zoom value to the central saved params
							fUrlParams.addParam("zoom", z);
							
							//redraw t
							var jsonUrl = fUrlParams.buildUrl(dataUrls.data(), extraParams);
							table.reset(jsonUrl, yuiTableConfig);
							//update links in title right
							fTitleLinks.updateParam("zoom", z);
						});
					}else{
						table.draw(jsonUrl, yuiTableConfig);
					}
					
					fTitle.hide();
				});
			});
			if (!ignoreLoad){
				fTitle.loadChart(startIndex);
			}
			
			
			return this;
		},
		
		//draw all zooms as individual charts
		//chartUrls.data() will store an array of data urls for multiple charts
		makeAllZoomsChart : function(config){
			fTitle = new fFrame.SingleTitle();
			fTitleLinks = fTitle.makeTitleLinks();
			
			makeSingleTitle(config);
			addTitleLinks(config);
			fZoom.hide();
			
			var dataUrls = config.chartUrls;
			var extraParams = config.extraParams;
			var benchMode = config.benchMode;
			var dataCallback = config.dataCallback;

			var chartSwf = dataUrls.swf();
			var mainChart = new fMiddle.MainChart(dataCallback);
			
			var index = 0;
			var sideboxData = dataUrls.sidebox(); 
			var sideBoxes = [];
			$.each(dataUrls.data(), function(){
				var chartUrl = fUrlParams.buildUrl(this, extraParams);
				mainChart.appendChart(chartSwf, chartUrl, index);
				
				if (sideboxData != null && sideboxData[index] != null){
					sideBoxes[index] = new mainChart.SideBoxes(index);
					addSideBoxes(sideBoxes[index], sideboxData[index], extraParams);
				}
				
				index++;
			});
			
			redrawFunc = function(extraParams){
				mainChart.redraw(extraParams);
				//does not do anything with sideboxes yet, this is currently usused and i think might not even work
				//TODO: add sidebox updates
			};
			
			if (benchMode != null && sideBoxes.length > 0){
				fFooter.checkBottomSpacing()
						.showBench()
						.clearBench()
						.addBenchmarkExplain();
				if (benchMode.normal != null){
					var cat = benchMode.normal.cat;
					var categories = benchMode.normal.categories;
					fFooter.addBenchmarkNormal(cat, categories, function(newBenchId, newBenchName){
						fUrlParams.addParam("benchmarkID", newBenchId);
						index = 0;
						$.each(dataUrls.data(), function(){
							if (sideboxData != null && sideboxData[index] != null){
								var sideBox = sideBoxes[index];
								var spark = sideboxData[index].spark;
								if (spark.showBench){
									sideBox.addSparkBoxUrl("Benchmark:<br/>" + newBenchName, fUrlParams.buildUrl(spark.url, extraParams), "benchBox");
								}
							}
							index++;
						});
						fUrlParams.removeParam("benchmarkID");
						fFooter.savedBenchId = newBenchId;
					}, fFooter.savedBenchId);
				} else if (benchMode.excluded != null){
					fFooter.addBenchmarkExcluded();
				} else if (benchMode.appStore != null){
					var b = benchMode.appStore;
					fFooter.addAppStoreRequest(
						b.appStoreName, 
						b.explanationKey, 
						b.projectId, 
						b.savedAppStoreId
					);
				}
			}else{
				fFooter.hideBench();
			}
			
			return this;
		},

		//redraw function not set below this as these are not built from MainChart (Fusion Chart)
		makeSparks : function(title, configArr, zoomModes){
			var sparkGroup = new fMiddle.SparkGroup();
			var drawSparks = function(){
				var i = 0;
				$.each(configArr, function(){
					var config = this;
					var dataUrls = config.chartUrls;
					var extraParams = config.extraParams;
					
					var lineUrl = fUrlParams.buildUrl(dataUrls.spark(), extraParams);
					var countUrl = fUrlParams.buildUrl(dataUrls.count(), extraParams);
					sparkGroup.draw(i, lineUrl, countUrl);
					i++;
				});
			}

			fTitle = new fFrame.SingleTitle();
			
			if (typeof title === "string" || title instanceof String){
				fTitle.addName(title);
			}else{
				fTitleLinks = fTitle.makeTitleLinks();
				
				makeSingleTitle(title);
				addTitleLinks(title);
			}
			

			if (zoomModes != undefined){
				makeZooms(zoomModes, function(z){
					//add the zoom value to the central saved params
					fUrlParams.addParam("zoom", z);
					//redraw chart
					drawSparks();
				 });
			}else{
				fZoom.hide();
				fZoom = null;
				drawSparks();
			}
			
			return this;
		},
		makeDropDownMap : function(configArray, sdkUrl, startIndex, ignoreChartLoad){
			fZoom.hide();
			fZoom = null;

			fTitle = new fFrame.DropDownTitle();
			fTitleLinks = fTitle.makeTitleLinks();
			
			var mapData = configArray[startIndex].chartUrls.data();
			var map = new fMiddle.FusionMap(fUrlParams.buildUrl(mapData), sdkUrl);
			
			$.each(configArray, function (index, config){
				var dataUrls = config.chartUrls;
				var name     = dataUrls.name();
				
				fTitle.addChart(name, function(){
					addTitleLinks(config);
					map.setXmlUrl(fUrlParams.buildUrl(dataUrls.data()));
					
					fTitle.hide();
				})
			});

			if (!ignoreChartLoad){
				fTitle.loadChart(startIndex);
			}
		},
		makeMap : function(config, sdkUrl){
			fZoom.hide();
			fZoom = null;

			fTitle = new fFrame.SingleTitle();
			fTitleLinks = fTitle.makeTitleLinks();
			
			makeSingleTitle(config);
			addTitleLinks(config);
			
			var dataUrls = config.chartUrls;
			var map = new fMiddle.FusionMap(fUrlParams.buildUrl(dataUrls.data()), sdkUrl);
			map.draw();
			
			return this;
		},
		makeTable : function(config, argName, tableConfig, toggleFunction){
			var name = argName || config.chartUrls.name() || "";
			fTitle = new fFrame.SingleTitle();
			fTitleLinks = fTitle.makeTitleLinks();
			
			fTitle.addName(name);
			addTitleLinks(config, toggleFunction);
			
			var dataUrls = config.chartUrls;
			var extraParams = config.extraParams;
			
			var table = new fMiddle.YuiTable(tableConfig.paginated, tableConfig.embedded);
			var jsonUrl = fUrlParams.buildUrl(dataUrls.data(), extraParams);
			var zoomModes = config.zoomModes;
			var geoZoomModes = config.geoZoomModes;
			
			redrawFunc = function(redrawParams){
				var combinedParams = $.extend({}, extraParams, redrawParams);
				var jsonUrl = fUrlParams.buildUrl(dataUrls.data(), combinedParams);
				table.reset(jsonUrl, tableConfig);
			}
			
			//zoom
			if (zoomModes != undefined){
				makeZooms(zoomModes, function(z){
					//add the zoom value to the central saved params
					fUrlParams.addParam("zoom", z);
					
					//redraw t
					var jsonUrl = fUrlParams.buildUrl(dataUrls.data(), extraParams);
					table.reset(jsonUrl, tableConfig);
					//update links in title right
					fTitleLinks.updateParam("zoom", z);
				});
			} else if (geoZoomModes != undefined){
				makeGeoZooms(geoZoomModes, function(z){
					fUrlParams.addParam("geographicZoomLevel", z);
					
					var jsonUrl = fUrlParams.buildUrl(dataUrls.data(), extraParams);
					
					//update links in title right
					fTitleLinks.updateParam("geographicZoomLevel", z);
					geoZoomModes.clickHandler(z);
				});
			} else {
				table.draw(jsonUrl, tableConfig);
			}
				
			return this;
		},
		makeShell : function(title, config, toggleFunction, zoomFunction){
			fTitle = new fFrame.SingleTitle()
				.addName(title)
			;
			fTitleLinks = fTitle.makeTitleLinks();
			addTitleLinks(config, toggleFunction);
			
			//zoom
			var zoomModes = config.zoomModes;
			var zFunction = zoomFunction || $.noop;
			if (zoomModes != undefined){
				makeZooms(zoomModes, function(z){
					//add the zoom value to the central saved params
					fUrlParams.addParam("zoom", z);
					
					//react to zoom change
					zFunction(z);
					
					//update links in title right
					fTitleLinks.updateParam("zoom", z);
				});
			}
			return this;
		},
		
		/**
		 * expects that controls in the header object will update a 
		 * Flurry.SimpleModelObject (utils.js) with params where the key/value
		 * can be added to all urls as &key=value.  then will use config
		 * to draw an otherwise standard singleChart.  
		 * 
		 *  It is expected that heaader changes will update title links, zooms, and
		 *  the chart itself, but not actually redraw the title.  
		 */
		makeControlHeaderChart : function($header, simpleModelObject, config){
			var model = simpleModelObject || new Flurry.SimpleModelObject();
			model.updateUrlParams(fUrlParams);

			fTitle = new fFrame.SingleTitle()
				.addHtml($header)
			;
			fTitleLinks = fTitle.makeTitleLinks();
			addTitleLinks(config);
			
			//will add zoom to url params
			var chartObj = drawFusionChart(config);
			redrawFunc = function(extraParams){
				chartObj.redraw(extraParams);
			};
			
			//find zoom
			var zoom = fMiddle.getBorderChart().find(".zoomDiv");
			if (zoom.length > 0){ //zoom exists
				zoom
					//won't catch starting state so we need to activate
					.on('zoomUpdate', function(e, z){
						$header.trigger("zoomUpdate", z);
					})
				;
			}
			
			var geoZoom = fMiddle.getBorderChart().find(".geoZoomDiv");
			if (geoZoom.length > 0){ //geoZoom exists
				geoZoom.
					on('geoZoomUpdate', function(e, z){
						$header.trigger("geoZoomUpdate", z);
					})
				;
			}
			
			model.attach(function(dataModel, changed){
				model.updateUrlParams(fUrlParams);

				if (changed){
					chartObj.redraw(dataModel);
				}else{
					chartObj.updateParams(dataModel); //don't draw chart yet here	
				}
				
				//find zoom
				if (zoom.length > 0){ //zoom exists
					zoom.find("a.activeLink").trigger("click.update");//doesn't draw chart
				}
				//find geoZoom
				if (geoZoom.length > 0){ //zoom exists
					geoZoom.find("a.activeLink").trigger("click.update");//doesn't draw chart
				}
			});
			
			return this;
		},
		addFooter : function(text){
			fFooter.addBottomText(text);
			return this;
		},
		joinZooms : function(otherChartModule){
			fZoom.joinZoom(otherChartModule.getZooms());
			
			return this;
		},
		//does not return this
		makeUrl : function(url, extraParams){
			return fUrlParams.buildUrl(url, extraParams);
		},
		updateUrlParam : function(param, value){
			fUrlParams.addParam(param,value);
		},
		getMiddle : function(){
			return fMiddle;
		},
		getTitle : function(){
			return fTitle;
		},
		getTitleLinks : function(){
			return fTitleLinks;
		},
		getFooter : function(){
			return fFooter;
		},
		getZooms : function(){
			return fZoom;
		}
	};
	
	return chartModulePublic;
};

Flurry.ChartTypes = Flurry.ChartTypes || function(){
	return {
		cookieName : "pct",		//pie chart type
		savedPieType : function(val){
			if (val === undefined){
				return getFlurryCookie(this.cookieName);
			}else{
				setFlurryCookie(this.cookieName, val);
			}
		}
	};
}();

Flurry.ZoomModes = Flurry.ZoomModes || function(){
	return {
		defaultZoom : "m",
		basic : true,

		hourly : false,
		hourlyBox : null,

		dayparting : false,
		daypartingBox : null,
		
		dailyBox : null,
		
		getDailyBadIntervalBox : function(max, current){
			var lightboxId = "dailyIntervalErrorBox";
			var box = new Flurry.Lightbox(lightboxId)
				.setTitle('Daily View Not Supported for this Interval')
				.setBody(
					'You cannot view daily data for this metric for a time period greater than ' + max + ' days. '
				+	'The current time frame is ' + current + ' days.'
				+ 	'<br/><br/>'
				+	'Please select a shorter time frame with the time drop-down filter, located on the right of the menu bar.'
				)
			;
			return box;
		},
		getHourlyBadIntervalBox : function(max, current){
			var lightboxId = "hourlyIntervalErrorBox";
			var box = new Flurry.Lightbox(lightboxId)
				.setTitle('Hourly View Not Supported for this Interval')
				.setBody(
					'You cannot view hourly data for this metric for a time period greater than ' + max + ' days. '
				+	'The current time frame is ' + current + ' days.'
				+ 	'<br/><br/>'
				+	'Please select a shorter time frame with the time drop-down filter, located on the right of the menu bar.'
				)
			;
			return box;
		},
		getHourlyBadParamsBox : function(){
			var lightboxId = "hourlyParamsErrorBox";
			var box = new Flurry.Lightbox(lightboxId)
				.setTitle('Hourly View Not Supported for these Parameters')
				.setBody(
					'Hourly view is not available for the version filter. '
				+	'Please view "All Versions" for the hourly view.'
				)
			;
			return box;
		},
		getDaypartingBadParamsBox : function(){
			var lightboxId = "daypartingParamsErrorBox";
			var box = new Flurry.Lightbox(lightboxId)
				.setTitle('Time of Day View Not Supported for these Parameters')
				.setBody(
					'Time of day view is not available for the version filter. '
				+	'Please view "All Versions" for the time of day view.'
				)
			;
			return box;
		},
		getDaypartingBadSdkBox : function(sdkUrl){
			var lightboxId = "daypartingSdkErrorBox";
			var box = new Flurry.Lightbox(lightboxId)
				.setTitle('Time of Day Metrics Not Supported for your SDK')
				.setBody(
					"The version of the SDK that you're currently using does not allow track locale information. "
				+	'The Time of Day metric cannot display without locale data. '
				+ 	'To utilize this feature, '
				+	'<a href="' + sdkUrl + '" class="colorMediumBlue spanHover bold">'
				+		'<span>download our latest SDK here</span>'
				+ 	'</a>.'
				)
			;
			return box;
		}
	};
}();

Flurry.ConfigHelper = Flurry.ConfigHelper || function(){
	return {
		cloneExtras : function(configArray, extraParams, zoomModes, benchMode, optProperties){
			$.each(configArray, function(){
				if (extraParams != undefined){
					this.extraParams = extraParams;
				}
				if (zoomModes != undefined){
					this.zoomModes = zoomModes;
				}
				if (benchMode != undefined){
					this.benchMode = benchMode;
				}
				if (optProperties != undefined){
					for (var x in optProperties){
						if (optProperties.hasOwnProperty(x)){
							this[x] = optProperties[x];
						}
					}
				}
			});
			return configArray;
		},
		zoomCheck : function(configArray){
			var arraySafe = configArray;
			if( Object.prototype.toString.call( configArray ) !== '[object Array]' ) {
			    arraySafe = [configArray];
			}
			$.each(arraySafe, function(index, value){
				var zoomSwfs = ["ScrollLine2D.swf", "ScrollCombi2D.swf"];
				
				var chartConfig = value;
				var chartSwf = chartConfig.chartUrls.swf();
				
				var zoomOk = false;
				$.each(zoomSwfs, function(index, value){
					if (chartSwf == value){
						zoomOk = true;
					}
				});
				if (!zoomOk){
					chartConfig.zoomModes = null;
				}					
			});
		}
	};
}();

Flurry.DashboardChartTypes = Flurry.DashboardChartTypes || function(){
	var pub = {
		"1" : {
			"name" : "Chart",
			"id" : 1,
			"multiple" : true,
			"hasHalf" : true,
			"preview" : "/images/ui/dashboard/mainChart.png",
			"description" : "Display one or more metrics as a full chart by clicking on the metrics below.  The order you click matters.",
			"draw" : function($div, widgets, config, halfMode){

				var chartUrls = {
					//usage
					"1" : (new Flurry.DataUrls()
							.data("/newUsersChart.do")
							.explain('analytics','newUsersOverTime_chart')
							.csv("/newUsersCsv.do")
							.name("New Users")
							.swf("ScrollLine2D.swf")),
					"2" : (new Flurry.DataUrls()
							.data("/usersChart.do")
							.explain('analytics','activeUsersOverTime_chart')
							.csv("/usersCsv.do")
							.name("Active Users")
							.swf("ScrollLine2D.swf")),
					"3" : (new Flurry.DataUrls()
							.data("/sessionsChart.do")
							.explain('analytics','sessionsOverTime_chart')
							.csv("/sessionsCsv.do")
							.name("Sessions")
							.swf("ScrollLine2D.swf")),
					"4" : (new Flurry.DataUrls()
							.data("/sessionLengthChart.do")
							.explain('analytics','sessionLengthOverTime_histogram')
							.csv("/sessionLengthCsv.do")
							.name("Session Length Distribution")
							.swf("ScrollColumn2D.swf")),
					"5" : (new Flurry.DataUrls()
							.data("/avgSessionIntervalChart.do")
							.explain('analytics','sessionLengthOverTime_chart')
							.csv("/avgSessionIntervalCsv.do")
							.name("Median Session Length")
							.swf("ScrollLine2D.swf")),
					"6" : (new Flurry.DataUrls()
							.data("/frequencyChart.do")
							.csv("/frequencyCsv.do")
							.name("Session Frequency")
							.swf("Pie2D.swf")),
					"7" : (new Flurry.DataUrls()
							.data("/sessionsPerDayChart.do")
							.explain('analytics','frequencyOfUseOverTime_chart')
							.csv("/sessionsPerDayCsv.do")
							.name("Session Frequency")
							.swf("ScrollLine2D.swf")),
					"8" : (new Flurry.DataUrls()
							.data("/rollingRetentionChart.do?completeUsers=true")
							.explain('analytics','lifecycle_chart')
							.csv("/rollingRetentionCsv.do?completeUsers=true")
							.name("Rolling Retention")
							.swf("ScrollCombi2D.swf")),
					"9" : (new Flurry.DataUrls()
							.data("/sessionsPerUserChart.do")
							.explain('analytics','lifecycle_chart')
							.csv("/sessionsPerUserCsv.do")
							.name("Sessions Per User")
							.swf("ScrollCombi2D.swf")),
					"10" : (new Flurry.DataUrls()
							.data("/usersRecentChart.do")
							.explain('analytics','recentUsers_chart')
							.csv("/usersRecentCsv.do")
							.name("Recent Users")
							.swf("ScrollLine2D.swf")),
					"11" : (new Flurry.DataUrls()
							.data("/usersRecentChart.do?asPercent=true")
							.explain('analytics','recentUsersPercent_chart')
							.csv("/usersRecentCsv.do")
							.name("Recent User Percentage")
							.swf("ScrollLine2D.swf")),
					"12" : (new Flurry.DataUrls()
							.data("/versionsTimeChart.do?limit=10")
							.explain('analytics','users_versions_chart')
							.csv("/versionsTimeCsv.do")
							.name("Top 10 Versions By Active Users")
							.swf("StackedArea2D.swf")),
					"13" : (new Flurry.DataUrls()
							.data("/versionsPieChart.do?limit=10")
							.explain('analytics','users_versions_piechart')
							.csv("/versionsPieCsv.do")
							.name("Top 10 Versions By Active Users Per Day")
							.swf("Pie2D.swf")),
					
					//audience
					"100" : (new Flurry.DataUrls()
							.data("/audienceUsersInCommonChart.do")
							.explain('analytics','interests_myApps')
							.csv("/conversionOverlapDetailedCsv.do?showNewUsers=true")
							.name("Users in Common with this App")
							.swf("MSBar2D.swf")),
					"101" : (new Flurry.DataUrls()
							.data("/audienceUsersAlsoLikeChart.do?asChart=true&asNewUsers=false&showGlobal=false")
							.explain('analytics','interests_categories_table')
							.csv("/audienceUsersAlsoLikeCsv.do?asNewUsers=false&showGlobal=false")
							.name("User Interest By Sessions")
							.swf("MSBar2D.swf")),
					"102" : (new Flurry.DataUrls()
							.data("/audienceUsersAlsoLikeChart.do?asChart=true&asNewUsers=true&showGlobal=false")
							.explain('analytics','interests_categories_table')
							.csv("/audienceUsersAlsoLikeCsv.do?asNewUsers=true&showGlobal=false")
							.name("User Interest By New Users")
							.swf("MSBar2D.swf")),
					"110" : (new Flurry.DataUrls()
							.data("/getAudienceAgeHistogram.do?useEstimates=false")
							.name("Age Distribution")
							.swf("ScrollColumn2D.swf")),
					"111" : (new Flurry.DataUrls()
							.data("/getAudienceAgeHistogram.do?useEstimates=true")
							.name("Estimated Age Distribution")
							.swf("ScrollColumn2D.swf")),
					"120" : (new Flurry.DataUrls()
							.data("/getAudienceGenderPieChart.do?useEstimates=false")
							.name("Gender Distribution")
							.swf("Pie2D.swf")),
					"121" : (new Flurry.DataUrls()
							.data("/getAudienceGenderPieChart.do?useEstimates=true")
							.name("Estimated Gender Distribution")
							.swf("Pie2D.swf")),
					
					//events
					"300" : (new Flurry.DataUrls()
							.data("/sessionEventsChart.do")
							.csv("/sessionEventsCsv.do")
							.name("Events Per Session")
							.swf("ScrollColumn2D.swf")),
					"301" : (new Flurry.DataUrls()
							.data("/eventUsersChart.do")
							.csv("/eventUsersCsv.do")
							.name("Unique Users Performing Event")
							.swf("ScrollLine2D.swf")),
					"302" : (new Flurry.DataUrls()
							.data("/sessionEventsCountChart.do")
							.csv("/sessionEventsCountCsv.do")
							.name("Total Events")
							.swf("ScrollLine2D.swf")),
					"303" : (new Flurry.DataUrls()
							.data("/sessionEventsAvgChart.do")
							.csv("/sessionEventsAvgCsv.do")
							.name("Avg Events Per Session")
							.swf("Pie2D.swf")),					
					"304" : (new Flurry.DataUrls()
							.data("/sessionEventsChart.do")
							.csv("/sessionEventsCsv.do")
							.name("Events Per Session")
							.swf("ScrollColumn2D.swf")),
					"305" : (new Flurry.DataUrls()
							.data("/eventUsersChart.do")
							.csv("/eventUsersCsv.do")
							.name("Unique Users Performing Event")
							.swf("ScrollLine2D.swf")),
					"306" : (new Flurry.DataUrls()
							.data("/sessionEventsCountChart.do")
							.csv("/sessionEventsCountCsv.do")
							.name("Total Events")
							.swf("ScrollLine2D.swf")),
					"307" : (new Flurry.DataUrls()
							.data("/sessionEventsAvgChart.do")
							.csv("/sessionEventsAvgCsv.do")
							.name("Avg Events Per Session")
							.swf("Pie2D.swf")),
					
					

					"350" : (new Flurry.DataUrls()
							.data("/eventParamsChart.do?pieLimit=10")
							.csv("/eventParamsCsv.do?pieLimit=500")
							.name("Parameter Distribution")
							.swf("Pie2D.swf")),

					//technical
					"210" : (new Flurry.DataUrls()
							.data("/totalExceptionsChart.do")
							.csv("/totalExceptionsCsv.do")
							.name("Total Errors")
							.swf("ScrollLine2D.swf")),
					"211" : (new Flurry.DataUrls()
							.data("/avgExceptionsChart.do")
							.csv("/avgExceptionsCsv.do")
							.name("Avg Errors Per Session")
							.swf("ScrollLine2D.swf")),
					"212" : (new Flurry.DataUrls()
							.data("/sessionExceptionsChart.do")
							.csv("/sessionExceptionsCsv.do")
							.name("Errors Per Session")
							.swf("ScrollColumn2D.swf"))
				};
				
				function makeChartUrl(chartUrls, widget){
					var dataUrlObj = chartUrls[widget.id];
					var name = dataUrlObj.name();
					var hasName = name != null && name.length > 0;
					var newChartUrl = $.extend(true, {}, dataUrlObj);
					
					if (hasName){
						//Adds the event name to the chart title
						if (widget.eventName != null && widget.eventName.length > 0){
							name += " : " + widget.eventName;
						}
						//Adds the parameter name to the chart title
						if (widget.eventParam != null && widget.eventParam.length > 0){
							name += " : " + $('<div />').text(widget.eventParam).html();
						}
						
						newChartUrl.name(name);
					}
										
					return newChartUrl;
				}
				
				function makeChartExtraParams(widget){
					var extraParams = {};
					
					if (widget.eventId > 0){
						extraParams.eventID = widget.eventId;
					}
					if (widget.eventParam != null && widget.eventParam.length > 0){
						extraParams.paramName = encodeURIComponent(widget.eventParam);
					}
					
					return extraParams;
				}
				
				if (widgets.length > 1){
					var configArray = [];
					
					for (var x in widgets){
						if (widgets.hasOwnProperty(x)){
							var widget = widgets[x];
							
							var chartUrl = makeChartUrl(chartUrls, widget);
							var extraParams = makeChartExtraParams(widget);
							configArray.push({
								"chartUrls" : chartUrl,
								"extraParams" : extraParams,
							});
						}
					}
					
					var chart = new Flurry.ChartModule($div.attr("id"), config.chartParams.clone());

					configArray = Flurry.ConfigHelper.cloneExtras(configArray, null, config.zoomModes, config.benchMode);
					Flurry.ConfigHelper.zoomCheck(configArray);

					chart
						.makeDropDownChart(configArray, 0)
					;
				}else if (widgets.length == 1){
					var hasSparkUrls = {
						//usage
						"1" : (new Flurry.DataUrls()
								.data("/newUsersChart.do")
								.explain('analytics','newUsersOverTime_chart')
								.csv("/newUsersCsv.do")
								.spark("/newUsersCount.do")
								.name("New Users")
								.swf("ScrollLine2D.swf")),
						"3" : (new Flurry.DataUrls()
								.data("/sessionsChart.do")
								.explain('analytics','sessionsOverTime_chart')
								.csv("/sessionsCsv.do")
								.spark("/sessionsCount.do")
								.name("Sessions")
								.swf("ScrollLine2D.swf")),
						"5" : (new Flurry.DataUrls()
								.data("/avgSessionIntervalChart.do")
								.explain('analytics','sessionLengthOverTime_chart')
								.csv("/avgSessionIntervalCsv.do")
								.spark("/avgSessionIntervalCount.do")
								.name("Median Session Length")
								.swf("ScrollLine2D.swf"))
					};
					
					var updateTitleZoom = hasSparkUrls[widgets[0].id] != null;
					
					var hasSparkChartUrls = $.extend(true, {}, chartUrls, hasSparkUrls);

					var chart = new Flurry.ChartModule($div.attr("id"), config.chartParams.clone());
					var chartUrl = makeChartUrl(hasSparkChartUrls, widgets[0]);
					var extraParams = makeChartExtraParams(widgets[0]);
					var chartConfig = $.extend(true, {}, {
						"chartUrls" : chartUrl,
						"extraParams" : extraParams,
					}, config);

					Flurry.ConfigHelper.zoomCheck(chartConfig);

					chart
						.makeSingleChart(chartConfig, updateTitleZoom)
					;
				}
			}
		},
		"2" : {
			"name" : "Spark Charts",
			"id" : 2,
			"multiple" : true,
			"hasHalf" : false,
			"preview" : "/images/ui/dashboard/sparks.png",
			"description" : "Display several metrics as simple summary spark lines by clicking on the metrics below.  The order you click matters.",
			"draw" : function($div, widgets, config){

				var chartUrls = {
					"1" : (new Flurry.DataUrls()
							.spark("/newUsersSpark.do")
							.count("/newUsersCount.do")),
					"2" : (new Flurry.DataUrls()
							.spark("/usersSpark.do")
							.count("/usersCount.do")),
					"3" : (new Flurry.DataUrls()
							.spark("/sessionsSpark.do")
							.count("/sessionsCount.do")),
					"4" : (new Flurry.DataUrls()
							.spark("/avgSessionIntervalSpark.do")
							.count("/avgSessionIntervalCount.do")),
					"5" : (new Flurry.DataUrls()
							.spark("/avgSessionIntervalSpark.do")
							.count("/avgSessionIntervalCount.do"))
				};

				var sparksArray = [];
				
				for (var x in widgets){
					if (widgets.hasOwnProperty(x)){
						var widget = widgets[x];
						if (chartUrls[widget.id]){
							sparksArray.push({
								"chartUrls" : chartUrls[widget.id]
							});
						}
					}
				}

				var sparks = new Flurry.ChartModule($div.attr("id"), config.chartParams.clone());
				sparksArray = Flurry.ConfigHelper.cloneExtras(sparksArray, {}, config.zoomModes, config.benchMode);
				
				sparks
					.makeSparks("Spark Charts", sparksArray, config.zoomModes)
				;
			}
		},
		"3" : {
			"name" : "Table",
			"id" : 3,
			"multiple" : false,
			"hasHalf" : false,
			"preview" : "/images/ui/dashboard/table.png",
			"description" : "Display a detailed breakdown of a single metric.",
			"draw" : function($div, widgets, config){
				var tableUrls = {
					"1" : (new Flurry.DataUrls()
								.data("/newUsersJson.do")
								.explain('analytics','newUsersOverTime_chart')
								.csv("/newUsersCsv.do")),
					"2" : (new Flurry.DataUrls()
								.data("/usersJson.do")
								.explain('analytics','activeUsersOverTime_chart')
								.csv("/usersCsv.do")),
					"3" : (new Flurry.DataUrls()
								.data("/sessionsJson.do")
								.explain('analytics','sessionsOverTime_table')
								.csv("/sessionsCsv.do"))
				};
				var tableConfig = $.extend({}, {
					"chartUrls" : tableUrls[widgets[0].id]
				}, config);
	
				var yuiTableConfigs = {
					"1" : {
						"schema" : {
							resultsList : "Response.Results",
						    fields: [{key:"Date", parser:"string"},
						    		{key:"DateSort", parser:"number"},
						            {key:"NewUsers", parser:"string"},
						            {key:"NewUsersSort", parser:"number"},
						            {key:"PercentNewUsers", parser:"string"}
						    ]
						},
						"columnDefs" : [
			    		    {key:"Date", 
			    		    	sortable:true, 
			    		    	sortOptions:{field:"DateSort"}
			    		    },
			    		    {key:"NewUsers",
			    			    label:"New Users",
			    		    	sortable:true, 
			    		    	sortOptions:{field:"NewUsersSort",defaultDir:YAHOO.widget.DataTable.CLASS_DESC}
			    		    },
			    	    	{key:"PercentNewUsers", 
			    	    		label:"% of New Users",
			    	    		sortable:true, 
			    	    		sortOptions:{field:"NewUsersSort",defaultDir:YAHOO.widget.DataTable.CLASS_DESC}}
			       		],
			       		"config" : {
			  				sortedBy:{key:"Date", dir:"desc"}
			  			},
			  			"paginated" : false
					},
					"2" : {
						"schema" : {
							resultsList : "Response.Results",
						    fields: [{key:"Date", parser:"string"},
						    		{key:"DateSort", parser:"number"},
						            {key:"Users", parser:"string"},
						            {key:"UsersSort", parser:"number"},
						            {key:"PercentUsers", parser:"string"}
						    ]
						},
						"columnDefs" : [
			    		    {key:"Date", 
			    		    	sortable:true, 
			    		    	sortOptions:{field:"DateSort"}
			    		    },
			    		    {key:"Users", 
			    		    	sortable:true, 
			    		    	sortOptions:{field:"UsersSort",defaultDir:YAHOO.widget.DataTable.CLASS_DESC}
			    		    },
			    	    	{key:"PercentUsers", 
			    	    		label:"% of Users",
			    	    		sortable:true, 
			    	    		sortOptions:{field:"UsersSort",defaultDir:YAHOO.widget.DataTable.CLASS_DESC}}
			       		],
			       		"config" : {
			  				sortedBy:{key:"Date", dir:"desc"}
			  			},
			  			"paginated" : false
					},
					"3" : {
						"schema" : {
							resultsList : "Response.Results",
						    fields: [{key:"Date", parser:"string"},
						    		{key:"DateSort", parser:"number"},
						            {key:"Sessions", parser:"string"},
						            {key:"SessionsSort", parser:"number"},
						            {key:"PercentSessions", parser:"string"}
						    ]
						},
						"columnDefs" : [
			         		{key:"Date", 
			       		    	sortable:true, 
			       		    	sortOptions:{field:"DateSort"}
			       		    },
			       		    {key:"Sessions", 
			       		    	sortable:true, 
			       		    	sortOptions:{field:"SessionsSort",
			           		    			 defaultDir:YAHOO.widget.DataTable.CLASS_DESC}
			       		    },
			       	    	{key:"PercentSessions", 
			       	    		label:"% of Sessions",
			       	    		sortable:true, 
			       	    		sortOptions:{field:"SessionsSort",defaultDir:YAHOO.widget.DataTable.CLASS_DESC}}
			       		],
			       		"config" : {
			  				sortedBy:{key:"Date", dir:"desc"}
			  			},
			  			"paginated" : false
					}
				};
	
	  			var table = new Flurry.ChartModule($div.attr("id"), config.chartParams.clone());
				table
					.makeTable(tableConfig, tableUrls[widgets[0].id].name(), yuiTableConfigs[widgets[0].id])
				;
			}
		},
		"4" : {
			"name" : "Map",
			"id" : 4,
			"multiple" : true,
			"hasHalf" : true,
			"preview" : "/images/ui/dashboard/map.png",
			"description" : "Displays one or more metrics broken up by geographic region. The order in which you click them matters.",
			"draw" : function($div, widgets, config, halfMode){
				var chartUrls = {
						"1" : (new Flurry.DataUrls()
							.data("/newUsersMap.do")
							.csv("/newUsersMapCsv.do")
							.explain('analytics','newUsers_map')
							.name("New Users Map")),
						"2" : (new Flurry.DataUrls()
							.data("/usersMap.do")
							.csv("/usersMapCsv.do")
							.explain('analytics','activeUsers_map')
							.name("Avg Active Users Map")),
						"3" : (new Flurry.DataUrls()
							.data("/sessionsMap.do")
							.csv("/sessionsMapCsv.do")
							.explain('analytics','sessions_map')
							.name("Sessions Map")),
						"5" : (new Flurry.DataUrls()
							.data("/sessionLengthMap.do")
							.csv("/sessionLengthMapCsv.do")
							.explain('analytics','avgSessionLength_map')
							.name("Median Sesion Length Map")),
						"11" : (new Flurry.DataUrls()
							.data("/usersRecentMap.do")
							.csv("/usersRecentMapCsv.do")
							.explain('analytics','recentUsers_map')
							.name("Recent Users Map"))
					};
					
					if(widgets.length > 1) {
						var mapsConfig = [];
						
						$.each(widgets, function (index, widget){
							if (chartUrls[widget.id]){
								mapsConfig.push({
									"chartUrls" : chartUrls[widget.id],
								});
							}
						});
						var map = new Flurry.ChartModule($div.attr("id"), config.chartParams.clone());
						
						mapsConfig = Flurry.ConfigHelper.cloneExtras(mapsConfig, {}, config.zoomModes, config.benchMode);
	
						map
							.makeDropDownMap(mapsConfig, null, 0)
						;
					} else {
						var mapConfig = $.extend({}, {
							"chartUrls" : chartUrls[widgets[0].id]
						}, config);

						var map = new Flurry.ChartModule($div.attr("id"), config.chartParams.clone());

						map
							.makeMap(mapConfig, null)
						;
					}
			}
		}
	};
	return pub;
}();

//i need to integrate these later
function setDropDownIndexStore(saveValue)
{
	$('#dropDownIndexStore').val(saveValue);
}

function getDropDownIndexStore()
{
	return $('#dropDownIndexStore').val();
}

