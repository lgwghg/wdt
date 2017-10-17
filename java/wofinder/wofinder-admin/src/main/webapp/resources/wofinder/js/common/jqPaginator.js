(function ($) {
    $.jqPaginator = function (el, options) {
        var self = this;

        self.$container = $(el);

        self.$container.data('jqPaginator', self);

        self.init = function () { 

            var opts = self.options = $.extend({}, $.jqPaginator.defaultOptions, options);

            if (!opts.totalPages && !opts.totalCounts) {
                throw new Error('[jqPaginator] totalCounts or totalPages is required');
            }

            if (!opts.totalPages && opts.totalCounts && !opts.pageSize) {
                throw new Error('[jqPaginator] pageSize is required');
            }

            if (!opts.totalPages && opts.totalCounts && opts.pageSize) {
                opts.totalPages = Math.ceil(opts.totalCounts / opts.pageSize);
            }

            if (opts.currentPage < 1 || opts.currentPage > opts.totalPages) {
                throw new Error('[jqPaginator] currentPage is incorrect');
            }

            if (opts.totalPages < 1) {
                throw new Error('[jqPaginator] totalPages cannot be less currentPage');
            }

            self.extendJquery();

            self.render();

            self.fireEvent(this.options.currentPage);
        };

        self.extendJquery = function () {
            $.fn.jqPaginatorHTML = function (s) {
                return s ? this.before(s).remove() : $('<p>').append(this.eq(0).clone()).html();
            };
        };

        self.render = function () {
            self.renderHtml();
            self.setStatus();
            self.bindEvents();
        };

        self.renderHtml = function () {
            var html = [];

            var pages = self.getPages();
            for (var i = 0, j = pages.length; i < j; i++) {
                html.push(self.buildItem('page', pages[i]));
            }
            var prev = self.options.currentPage - 1;
            if(prev < 1) {
            	prev = 1;
            }
            var next = self.options.currentPage + 1;
            if(next > self.options.totalPages) {
            	next = self.options.totalPages;
            }

            self.isEnable('prev') && html.unshift(self.buildItem('prev', prev));
            self.isEnable('first') && html.unshift(self.buildItem('first', 1));
            self.isEnable('statistics') && html.unshift(self.buildItem('statistics'));
            self.isEnable('next') && html.push(self.buildItem('next', next));
            self.isEnable('last') && html.push(self.buildItem('last', self.options.totalPages));

            if (self.options.wrapper) {
                self.$container.html($(self.options.wrapper).html(html.join('')).jqPaginatorHTML());
            } else {
                self.$container.html(html.join(''));
            }
        };

        self.buildItem = function (type, pageData) {
            var html = self.options[type]
                .replace(/{{page}}/g, pageData)
                .replace(/{{totalPages}}/g, self.options.totalPages)
                .replace(/{{totalCounts}}/g, self.options.totalCounts);

            return $(html).attr({
                'jp-role': type,
                'jp-data': pageData
            }).jqPaginatorHTML();
        };

        self.setStatus = function () {
            var options = self.options;

            if (!self.isEnable('first') || options.currentPage === 1) {
                $('[jp-role=first]', self.$container).addClass(options.disableClass);
            }
            if (!self.isEnable('prev') || options.currentPage === 1) {
                $('[jp-role=prev]', self.$container).addClass(options.disableClass);
            }
            if (!self.isEnable('next') || options.currentPage >= options.totalPages) {
                $('[jp-role=next]', self.$container).addClass(options.disableClass);
            }
            if (!self.isEnable('last') || options.currentPage >= options.totalPages) {
                $('[jp-role=last]', self.$container).addClass(options.disableClass);
            }

            $('[jp-role=page]', self.$container).removeClass(options.activeClass);
            $('[jp-role=page][jp-data=' + options.currentPage + ']', self.$container).addClass(options.activeClass);
        };

        self.getPages = function () {
            var pages = [],
                visiblePages = self.options.visiblePages,
                currentPage = self.options.currentPage,
                totalPages = self.options.totalPages;

            if (visiblePages > totalPages) {
                visiblePages = totalPages;
            }

            var half = Math.floor(visiblePages / 2);
            var start = currentPage - half + 1 - visiblePages % 2;
            var end = currentPage + half;

            if (start < 1) {
                start = 1;
                end = visiblePages;
            }
            if (end > totalPages) {
                end = totalPages;
                start = 1 + totalPages - visiblePages;
            }

            var itPage = start;
            while (itPage <= end) {
                pages.push(itPage);
                itPage++;
            }

            return pages;
        };

        self.isEnable = function (type) {
            return self.options[type] && typeof self.options[type] === 'string';
        };

        self.switchPage = function (pageIndex) {
            self.options.currentPage = pageIndex;
            self.render();
        };

        self.fireEvent = function (pageIndex) {
            return (typeof self.options.onPageChange !== 'function') || (self.options.onPageChange(pageIndex) !== false);
        };

        self.callMethod = function (method, options) {
            switch (method) {
                case 'option':
                    self.options = $.extend({}, self.options, options);
                    self.render();
                    break;
                case 'destroy':
                    self.$container.empty();
                    self.$container.removeData('jqPaginator');
                    break;
                default :
                    throw new Error('[jqPaginator] method "' + method + '" does not exist');
            }

            return self.$container;
        };

        self.bindEvents = function () {
            var opts = self.options;

            self.$container.off();
            self.$container.on('click', '[jp-role]', function () {
                var $el = $(this);
                if ($el.hasClass(opts.disableClass) || $el.hasClass(opts.activeClass)) {
//                    return;
                }

                var pageIndex = +$el.attr('jp-data');
                if (self.fireEvent(pageIndex)) {
                    self.switchPage(pageIndex);
                }
            });
        };

        self.init();

        return self.$container;
    };

    $.jqPaginator.defaultOptions = {
        wrapper: '',
        first: '',
        prev: '',
        next: '',
        last: '',
        page: '',
        totalPages: 0,
        totalCounts: 0,
        pageSize: 0,
        currentPage: 1,
        visiblePages: 7,
        disableClass: '',
        activeClass: 'choose',
        onPageChange: null
    };

    $.fn.jqPaginator = function () {
        var self = this,
            args = Array.prototype.slice.call(arguments);

        if (typeof args[0] === 'string') {
            var $instance = $(self).data('jqPaginator');
            if (!$instance) {
                throw new Error('[jqPaginator] the element is not instantiated');
            } else {
                return $instance.callMethod(args[0], args[1]);
            }
        } else {
            return new $.jqPaginator(this, args[0]);
        }
    };
    
    $.pagination = function (id, options, callBack) {
    	if(id == "" || id == undefined) {
    		throw new Error('id is required');
    	}
    	if(options == "" || options == undefined) {
    		throw new Error('options is required');
    	}
    	if(options.isLoad && !options.pageCreated) {// 加载过一次，尚未创建表单
    		return;
    	}
    	var pageSize = options.pageSize;
    	if(pageSize == "" || pageSize == undefined) {
    		pageSize = 10;
    	}
    	var visiblePages = options.visiblePages;
		if(visiblePages == "" || visiblePages == undefined) {
			visiblePages = 6;
		}
    	options.pageSize = pageSize;
    	options.visiblePages = visiblePages;
    	var ajaxType = options.ajaxType;
    	if(ajaxType == "" || ajaxType == undefined) {
    		ajaxType = "post";
    	}
    	$.ajax({
			url: options.url,
			data: {"gridPager": JSON.stringify(options)},
			type: ajaxType,
			dataType: "json",
			success: function(backData) {
				if(backData.isSuccess) {
					var nowPage = backData.nowPage;
					if(nowPage == 0) {
						nowPage = 1;// 从1开始
					}
					if(backData.list) {
						options.isLoad = true;
						if(!options.pageCreated && backData.list.length > 0) {// 只创建一次分页
							$.jqPaginator('#'+id, {
								totalPages: backData.pageCount,
								pageSize: pageSize,
								currentPage: nowPage,
								visiblePages: visiblePages,
								first: '',
								prev: '<li class="prve"><a href="javascript:void(0);">上一页</a></li>',
								next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
								last: '',
								page: '<li><a href="javascript:void(0);">{{page}}</a></li>',
								onPageChange: function (num) {
									options.nowPage = num;
									$.pagination(id, options, callBack);
								}
							});
							options.pageCreated = true;
						}
					}
					
					if (callBack) {
						callBack(backData.list, backData.recordCount);
					}
				} else {
					layer.alert(callBack.message, {title:"",icon: 5,time: 0});
				}
			}
		});
    	
    };

})(jQuery);