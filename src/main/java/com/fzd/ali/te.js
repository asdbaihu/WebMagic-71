!function (a) {
    function i(a) {
        this.init(a)
    }

    var e = {id: "sw_mod_filter_area", end: 0};
    a.extend(i.prototype, {
        init: function (i) {
            this.config = a.extend(!0, {}, e, i);
            var t = a("#" + this.config.id);
            this.value = this.config.value || "\u6240\u6709\u5730\u533a", this.prov = this.config.prov || "", this.city = this.config.city || "", a.isFunction(this.config.onchange) && (this.onchange = this.config.onchange), this._createHTML(t, this.value, this.prov, this.city), this._bindEvent(t)
        },
        getInfo: function () {
            return {value: this.value, prov: this.prov, city: this.city}
        },
        createIndustryDistrict: function (a, i) {
            var e = "";
            if (i) {
                e += '<div class="sw-ui-area-industryDistrict">\u4ea7\u54c1\u96c6\u4e2d\u5730</div>', e += '<ul class="sw-ui-area-industryDistrict-area fd-clear">';
                var t = new Array;
                t = i.split("&");
                for (var r = 0; r < t.length; r++) {
                    var n = t[r], s = n;
                    (s.indexOf("\u5e02") > 0 || s.indexOf("\u53bf") > 0) && s.indexOf("\u533a") > 0 && (s = s.replace("\u5e02", "-"), s = s.replace("\u53bf", "-"), s = s.replace("\u533a", "")), e += '<li class="sw-ui-area-box-item items">', e += "<a", e += a == n ? ' class="sw-ui-area-box-link sw-ui-area-box-nfocus"' : ' class="sw-ui-area-box-link"', e += ' c="', e += n, e += '"', e += ' v="', e += n, e += '"', e += ' d="', e += n, e += '"', e += ' p=""', e += ' href="#"', e += ">", e += s, e += "</a>", e += "</li>"
                }
                e += "</ul>"
            }
            return e
        },
        _createHTML: function (i, e, t, r) {
            function n(a) {
                return e == a ? (n = function () {
                    return ""
                }, "sw-ui-area-box-focus") : ""
            }

            function s(a, i) {
                if (t == i) {
                    s = function () {
                        return ""
                    };
                    for (var e = 0, r = a.city.length; r > e;)if (c(a.city[e++]))return "sw-ui-area-box-focus";
                    return "sw-ui-area-box-nfocus"
                }
                return ""
            }

            function c(a) {
                return e == a ? !0 : ""
            }

            function o(a) {
                return e == a ? (o = function () {
                    return ""
                }, "sw-ui-area-box-focus") : ""
            }

            var u = this, l = a("#" + this.config.id).attr("currentProvince"), v = a("#" + this.config.id).attr("industrydistricts"), m = '<span class="sm-filter-Area-Selected">' + e + '</span><div class="sw-ui-area-box"><div class="sw-ui-area-bg"></div>';
            l ? (m += '<div class="sw-ui-area-body"><div class="sw-ui-area-ab-all"><a class="sw-ui-area-box-link sw-area-abAll-link ' + n("\u6240\u6709\u5730\u533a") + '" href="#" p="" c="" v="\u6240\u6709\u5730\u533a">\u6240\u6709\u5730\u533a</a>', m += function () {
                for (var a, i = "", e = 0, t = 0, r = (u.provs.length, ""); e < u.provs.length;)if (r = u.provs[e++], r.name == l) {
                    for (t = 0, a = r.city.length, i += '<div class="sw-ui-area-abAll-nearArea">\u9644\u8fd1\u5730\u533a\uff1a</div><div class="sw-ui-area-ab-prov sw-ui-area-abAll-abpd"><div class="sw-ui-area-abAll-abpd-item"><a class="sw-ui-area-box-link sw-ui-area-abAll-abpd-city ' + s(r, r.name) + '" href="#" p="' + r.name + '" c="" v="' + r.name + '" searchtrace="sale_search_sort_province_ip">' + r.name.substr(0, 5) + '</a><ul class="sw-ui-area-ab-prov-items">'; a > t;)i += '<li class="sw-ui-area-box-item sw-ui-area-ab-prov-item"><a class="sw-ui-area-box-link sw-ui-area-ab-prov-itemLink ' + o(r.city[t]) + '" href="#" p="' + r.name + '" c="' + r.city[t] + '"' + (r.city[t].length > 5 ? ' title="' + r.city[t] + '"' : "") + ' v="' + r.city[t] + '" searchtrace="sale_search_sort_city_ip">' + r.city[t++].substr(0, 5) + "</a></li>";
                    i += "</ul></div></div></div>"
                }
                return i
            }(), m += u.createIndustryDistrict(e, v)) : (m += '<div class="sw-ui-area-body"><div class="sw-ui-area-ab-all"><a class="sw-ui-area-box-link sw-area-abAll-link ' + n("\u6240\u6709\u5730\u533a") + '" href="#" p="" c="" v="\u6240\u6709\u5730\u533a">\u6240\u6709\u5730\u533a</a></div>', m += u.createIndustryDistrict(e, v)), m += function () {
                for (var a = "", i = 0, e = u.areas.length, t = ""; e > i;)t = u.areas[i++], a += '<li class="sw-ui-area-box-item sw-ui-area-abArea-item"><a  class="sw-ui-area-box-link ' + n(t.name) + '" href="#" p="' + t.province + '" c="" v="' + t.name + '">' + t.name.substr(0, 5) + "</a></li>";
                return '<ul class="sw-ui-area-abArea fd-clear">' + a + "</ul>"
            }(), m += function () {
                for (var a, i = "", e = 0, t = 0, r = u.provs.length, n = ""; r > e;) {
                    for (n = u.provs[e++], t = 0, a = n.city.length, i += '<li class="sw-ui-area-box-item sw-ui-area-abProv-im"><a class="sw-ui-area-box-link sw-ui-area-ab-prov-itemLink ' + s(n, n.name) + '" href="#" p="' + n.name + '" c="" v="' + n.name + '">' + n.name + '</a><ul class="sw-ui-area-ab-prov-items">'; a > t;)i += '<li class="sw-ui-area-box-item"><a class="sw-ui-area-box-link sw-ui-area-abProv-itemsubLink ' + o(n.city[t]) + '" href="#" p="' + n.name + '" c="' + n.city[t] + '"' + (n.city[t].length > 5 ? ' title="' + n.city[t] + '"' : "") + ' v="' + n.city[t] + '">' + n.city[t++].substr(0, 5) + "</a></li>";
                    i += "</ul></li>"
                }
                return '<ul class="sw-ui-area-ab-prov">' + i + "</ul></div></div>"
            }(), i.html(m)
        },
        _bindEvent: function (i) {
            i.find(".sw-ui-area-abProv-im").each(function (i) {
                var e, t = a(this);
                t.bind("mouseover", function (a) {
                    clearTimeout(e), e = setTimeout(function () {
                        t.addClass("sw-ui-area-ab-prov-show")
                    }, 200)
                }), t.bind("mouseout", function (a) {
                    clearTimeout(e), e = setTimeout(function () {
                        t.removeClass("sw-ui-area-ab-prov-show")
                    }, 200)
                })
            });
            var e, t = this, r = i.find(".sw-ui-area-box");
            i.bind("mouseover", function (a) {
                clearTimeout(e), e = setTimeout(function () {
                    r.show()
                }, 200)
            }), i.bind("mouseout", function (a) {
                clearTimeout(e), e = setTimeout(function () {
                    r.hide()
                }, 200)
            }), r.bind("click", function (i) {
                var e = i.target;
                if ("A" == e.nodeName.toUpperCase()) {
                    var n = e.getAttribute("v"), s = e.getAttribute("p"), c = e.getAttribute("c"), o = e.getAttribute("d");
                    return (s != t.prov || c != t.city || n != t.value) && (t.value = n, t.prov = s, t.city = c, t.district = o, a(e).addClass("focus"), t.onchange(), r.hide()), !1
                }
            })
        },
        areas: [{
            name: "\u6c5f\u6d59\u6caa",
            province: "\u6c5f\u82cf,\u6d59\u6c5f,\u4e0a\u6d77"
        }, {
            name: "\u534e\u4e1c\u533a",
            province: "\u5c71\u4e1c,\u6c5f\u82cf,\u5b89\u5fbd,\u6d59\u6c5f,\u798f\u5efa,\u4e0a\u6d77"
        }, {
            name: "\u534e\u5357\u533a",
            province: "\u5e7f\u4e1c,\u5e7f\u897f,\u6d77\u5357"
        }, {
            name: "\u534e\u4e2d\u533a",
            province: "\u6e56\u5317,\u6e56\u5357,\u6cb3\u5357,\u6c5f\u897f"
        }, {
            name: "\u534e\u5317\u533a",
            province: "\u5317\u4eac,\u5929\u6d25,\u6cb3\u5317,\u5c71\u897f,\u5185\u8499\u53e4"
        }, {name: "\u5317\u4eac", province: "\u5317\u4eac"}, {
            name: "\u4e0a\u6d77",
            province: "\u4e0a\u6d77"
        }, {name: "\u5929\u6d25", province: "\u5929\u6d25"}, {
            name: "\u91cd\u5e86",
            province: "\u91cd\u5e86"
        }, {
            name: "\u6d77\u5916", province: "\u6d77\u5916"
        }],
        provs: [{
            name: "\u5e7f\u4e1c",
            city: ["\u5e7f\u5dde", "\u6df1\u5733", "\u73e0\u6d77", "\u6f6e\u5dde", "\u4e2d\u5c71", "\u4e1c\u839e", "\u4f5b\u5c71", "\u60e0\u5dde", "\u6c55\u5934", "\u6c55\u5c3e", "\u97f6\u5173", "\u6e5b\u6c5f", "\u8087\u5e86", "\u6cb3\u6e90", "\u6c5f\u95e8", "\u63ed\u9633", "\u8302\u540d", "\u6885\u5dde", "\u6e05\u8fdc", "\u9633\u6c5f", "\u4e91\u6d6e"]
        }, {
            name: "\u6d59\u6c5f",
            city: ["\u676d\u5dde", "\u5b81\u6ce2", "\u6e29\u5dde", "\u7ecd\u5174", "\u53f0\u5dde", "\u5609\u5174", "\u91d1\u534e", "\u4e3d\u6c34", "\u6e56\u5dde", "\u8862\u5dde", "\u821f\u5c71"]
        }, {
            name: "\u6c5f\u82cf",
            city: ["\u5357\u4eac", "\u82cf\u5dde", "\u65e0\u9521", "\u5e38\u5dde", "\u6dee\u5b89", "\u9547\u6c5f", "\u626c\u5dde", "\u5f90\u5dde", "\u8fde\u4e91\u6e2f", "\u5357\u901a", "\u5bbf\u8fc1", "\u6cf0\u5dde", "\u76d0\u57ce"]
        }, {
            name: "\u5c71\u4e1c",
            city: ["\u6d4e\u5357", "\u9752\u5c9b", "\u70df\u53f0", "\u6d4e\u5b81", "\u6ee8\u5dde", "\u83b1\u829c", "\u65e5\u7167", "\u6f4d\u574a", "\u6dc4\u535a", "\u5fb7\u5dde", "\u5a01\u6d77", "\u4e1c\u8425", "\u83cf\u6cfd", "\u804a\u57ce", "\u4e34\u6c82", "\u6cf0\u5b89", "\u67a3\u5e84"]
        }, {
            name: "\u6cb3\u5317",
            city: ["\u77f3\u5bb6\u5e84", "\u4fdd\u5b9a", "\u6ca7\u5dde", "\u79e6\u7687\u5c9b", "\u627f\u5fb7", "\u90af\u90f8", "\u5510\u5c71", "\u90a2\u53f0", "\u5eca\u574a", "\u8861\u6c34", "\u5f20\u5bb6\u53e3"]
        }, {
            name: "\u6cb3\u5357",
            city: ["\u90d1\u5dde", "\u6d1b\u9633", "\u5f00\u5c01", "\u7126\u4f5c", "\u5b89\u9633", "\u5357\u9633", "\u5468\u53e3", "\u5546\u4e18", "\u65b0\u4e61", "\u9e64\u58c1", "\u5e73\u9876\u5c71", "\u4e09\u95e8\u5ce1", "\u4fe1\u9633", "\u8bb8\u660c", "\u9a7b\u9a6c\u5e97", "\u6f2f\u6cb3", "\u6fee\u9633"]
        }, {
            name: "\u798f\u5efa",
            city: ["\u798f\u5dde", "\u53a6\u95e8", "\u6cc9\u5dde", "\u6f33\u5dde", "\u9f99\u5ca9", "\u5357\u5e73", "\u5b81\u5fb7", "\u8386\u7530", "\u4e09\u660e"]
        }, {
            name: "\u8fbd\u5b81",
            city: ["\u6c88\u9633", "\u5927\u8fde", "\u978d\u5c71", "\u4e39\u4e1c", "\u629a\u987a", "\u672c\u6eaa", "\u671d\u9633", "\u94c1\u5cad", "\u9526\u5dde", "\u8fbd\u9633", "\u961c\u65b0", "\u846b\u82a6\u5c9b", "\u76d8\u9526", "\u8425\u53e3"]
        }, {
            name: "\u5b89\u5fbd",
            city: ["\u5408\u80a5", "\u829c\u6e56", "\u9a6c\u978d\u5c71", "\u6dee\u5357", "\u868c\u57e0", "\u9ec4\u5c71", "\u961c\u9633", "\u6dee\u5317", "\u94dc\u9675", "\u4eb3\u5dde", "\u5ba3\u57ce", "\u5b89\u5e86", "\u5de2\u6e56", "\u6c60\u5dde", "\u516d\u5b89", "\u6ec1\u5dde", "\u5bbf\u5dde"]
        }, {
            name: "\u5e7f\u897f",
            city: ["\u5357\u5b81", "\u6842\u6797", "\u5317\u6d77", "\u67f3\u5dde", "\u68a7\u5dde", "\u7389\u6797", "\u767e\u8272", "\u5d07\u5de6", "\u8d35\u6e2f", "\u6cb3\u6c60", "\u8d3a\u5dde", "\u6765\u5bbe", "\u9632\u57ce\u6e2f", "\u94a6\u5dde"]
        }, {
            name: "\u5c71\u897f",
            city: ["\u592a\u539f", "\u5927\u540c", "\u664b\u57ce", "\u664b\u4e2d", "\u4e34\u6c7e", "\u5415\u6881", "\u6714\u5dde", "\u957f\u6cbb", "\u5ffb\u5dde", "\u9633\u6cc9", "\u8fd0\u57ce"]
        }, {
            name: "\u6d77\u5357",
            city: ["\u6d77\u53e3", "\u4e09\u4e9a", "\u743c\u6d77", "\u4e1c\u65b9", "\u510b\u5dde", "\u4e07\u5b81", "\u6587\u660c", "\u5b9a\u5b89\u53bf", "\u4e94\u6307\u5c71", "\u5c6f\u660c\u53bf", "\u6f84\u8fc8\u53bf", "\u4e34\u9ad8\u53bf", "\u767d\u6c99\u9ece\u65cf\u81ea\u6cbb\u53bf", "\u660c\u6c5f\u9ece\u65cf\u81ea\u6cbb\u53bf", "\u4e50\u4e1c\u9ece\u65cf\u81ea\u6cbb\u53bf", "\u9675\u6c34\u9ece\u65cf\u81ea\u6cbb\u53bf", "\u743c\u4e2d\u9ece\u65cf\u82d7\u65cf\u81ea\u6cbb\u53bf", "\u4fdd\u4ead\u9ece\u65cf\u82d7\u65cf\u81ea\u6cbb\u53bf"]
        }, {
            name: "\u5185\u8499\u53e4",
            city: ["\u547c\u548c\u6d69\u7279", "\u5305\u5934", "\u8d64\u5cf0", "\u9102\u5c14\u591a\u65af", "\u547c\u4f26\u8d1d\u5c14", "\u963f\u62c9\u5584\u76df", "\u901a\u8fbd", "\u4e4c\u6d77", "\u5174\u5b89\u76df", "\u5df4\u5f66\u6dd6\u5c14", "\u4e4c\u5170\u5bdf\u5e03\u76df", "\u9521\u6797\u90ed\u52d2\u76df"]
        }, {
            name: "\u5409\u6797",
            city: ["\u957f\u6625", "\u5409\u6797", "\u56db\u5e73", "\u901a\u5316", "\u767d\u57ce", "\u767d\u5c71", "\u8fbd\u6e90", "\u677e\u539f", "\u5ef6\u8fb9\u671d\u9c9c\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u9ed1\u9f99\u6c5f",
            city: ["\u54c8\u5c14\u6ee8", "\u5927\u5e86", "\u4f73\u6728\u65af", "\u9e64\u5c97", "\u7261\u4e39\u6c5f", "\u9ed1\u6cb3", "\u9e21\u897f", "\u4e03\u53f0\u6cb3", "\u9f50\u9f50\u54c8\u5c14", "\u53cc\u9e2d\u5c71", "\u7ee5\u5316", "\u4f0a\u6625", "\u5927\u5174\u5b89\u5cad"]
        }, {
            name: "\u6e56\u5317",
            city: ["\u6b66\u6c49", "\u9ec4\u5188", "\u9ec4\u77f3", "\u8346\u95e8", "\u8346\u5dde", "\u6f5c\u6c5f", "\u5b9c\u660c", "\u9102\u5dde", "\u5341\u5830", "\u968f\u5dde", "\u5929\u95e8", "\u4ed9\u6843", "\u54b8\u5b81", "\u8944\u6a0a", "\u5b5d\u611f", "\u795e\u519c\u67b6\u6797\u533a", "\u6069\u65bd\u571f\u5bb6\u65cf\u82d7\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u6e56\u5357",
            city: ["\u957f\u6c99", "\u5e38\u5fb7", "\u682a\u6d32", "\u5cb3\u9633", "\u90f4\u5dde", "\u6000\u5316", "\u6e58\u6f6d", "\u5f20\u5bb6\u754c", "\u8861\u9633", "\u5a04\u5e95", "\u90b5\u9633", "\u76ca\u9633", "\u6c38\u5dde", "\u6e58\u897f\u571f\u5bb6\u65cf\u82d7\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u6c5f\u897f",
            city: ["\u5357\u660c", "\u4e0a\u9976", "\u629a\u5dde", "\u8d63\u5dde", "\u4e5d\u6c5f", "\u9e70\u6f6d", "\u5409\u5b89", "\u666f\u5fb7\u9547", "\u840d\u4e61", "\u65b0\u4f59", "\u5b9c\u6625"]
        }, {
            name: "\u5b81\u590f",
            city: ["\u94f6\u5ddd", "\u56fa\u539f", "\u77f3\u5634\u5c71", "\u5434\u5fe0", "\u4e2d\u536b"]
        }, {
            name: "\u65b0\u7586",
            city: ["\u4e4c\u9c81\u6728\u9f50", "\u54c8\u5bc6", "\u548c\u7530", "\u5580\u4ec0", "\u5410\u9c81\u756a", "\u963f\u514b\u82cf", "\u963f\u62c9\u5c14", "\u77f3\u6cb3\u5b50", "\u4e94\u5bb6\u6e20", "\u514b\u62c9\u739b\u4f9d", "\u56fe\u6728\u8212\u514b", "\u660c\u5409\u56de\u65cf\u81ea\u6cbb\u5dde", "\u4f0a\u7281\u54c8\u8428\u514b\u81ea\u6cbb\u5dde", "\u5df4\u97f3\u90ed\u695e\u8499\u53e4\u81ea\u6cbb\u5dde", "\u535a\u5c14\u5854\u62c9\u8499\u53e4\u81ea\u6cbb\u5dde", "\u514b\u5b5c\u52d2\u82cf\u67ef\u5c14\u514b\u5b5c\u81ea\u6cbb\u5dde", "\u5854\u57ce\u5730\u533a", "\u963f\u52d2\u6cf0\u5730\u533a"]
        }, {
            name: "\u9752\u6d77",
            city: ["\u897f\u5b81", "\u6d77\u4e1c", "\u679c\u6d1b\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u6d77\u5317\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u6d77\u5357\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u9ec4\u5357\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u7389\u6811\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u6d77\u897f\u8499\u53e4\u65cf\u85cf\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u9655\u897f",
            city: ["\u897f\u5b89", "\u54b8\u9633", "\u6c49\u4e2d", "\u5b89\u5eb7", "\u5b9d\u9e21", "\u5546\u6d1b", "\u94dc\u5ddd", "\u6e2d\u5357", "\u5ef6\u5b89", "\u6986\u6797"]
        }, {
            name: "\u7518\u8083",
            city: ["\u5170\u5dde", "\u767d\u94f6", "\u9152\u6cc9", "\u5b9a\u897f", "\u5609\u5cea\u5173", "\u91d1\u660c", "\u5e86\u9633", "\u9647\u5357", "\u5e73\u51c9", "\u5929\u6c34", "\u6b66\u5a01", "\u5f20\u6396", "\u7518\u5357\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u4e34\u590f\u56de\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u56db\u5ddd",
            city: ["\u6210\u90fd", "\u5b9c\u5bbe", "\u7ef5\u9633", "\u5df4\u4e2d", "\u6500\u679d\u82b1", "\u8fbe\u5dde", "\u5fb7\u9633", "\u9042\u5b81", "\u5e7f\u5b89", "\u5e7f\u5143", "\u4e50\u5c71", "\u6cf8\u5dde", "\u7709\u5c71", "\u5357\u5145", "\u5185\u6c5f", "\u96c5\u5b89", "\u8d44\u9633", "\u81ea\u8d21", "\u7518\u5b5c\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u51c9\u5c71\u5f5d\u65cf\u81ea\u6cbb\u5dde", "\u963f\u575d\u85cf\u65cf\u7f8c\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u4e91\u5357",
            city: ["\u6606\u660e", "\u4fdd\u5c71", "\u4e3d\u6c5f", "\u7389\u6eaa", "\u662d\u901a", "\u4e34\u6ca7", "\u66f2\u9756", "\u666e\u6d31", "\u695a\u96c4\u5f5d\u65cf\u81ea\u6cbb\u5dde", "\u5927\u7406\u767d\u65cf\u81ea\u6cbb\u5dde", "\u8fea\u5e86\u85cf\u65cf\u81ea\u6cbb\u5dde", "\u6012\u6c5f\u5088\u5088\u65cf\u81ea\u6cbb\u5dde", "\u6587\u5c71\u58ee\u65cf\u82d7\u65cf\u81ea\u6cbb\u5dde", "\u897f\u53cc\u7248\u7eb3\u50a3\u65cf\u81ea\u6cbb\u5dde", "\u5fb7\u5b8f\u50a3\u65cf\u666f\u9887\u65cf\u81ea\u6cbb\u5dde", "\u7ea2\u6cb3\u54c8\u5c3c\u65cf\u5f5d\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u8d35\u5dde",
            city: ["\u8d35\u9633", "\u5b89\u987a", "\u6bd5\u8282", "\u94dc\u4ec1", "\u9075\u4e49", "\u516d\u76d8\u6c34", "\u9ed4\u4e1c\u5357\u82d7\u65cf\u4f97\u65cf\u81ea\u6cbb\u5dde", "\u9ed4\u5357\u5e03\u4f9d\u65cf\u82d7\u65cf\u81ea\u6cbb\u5dde", "\u9ed4\u897f\u5357\u5e03\u4f9d\u65cf\u82d7\u65cf\u81ea\u6cbb\u5dde"]
        }, {
            name: "\u897f\u85cf",
            city: ["\u62c9\u8428", "\u963f\u91cc", "\u660c\u90fd", "\u6797\u829d", "\u90a3\u66f2", "\u65e5\u5580\u5219", "\u5c71\u5357"]
        }, {
            name: "\u53f0\u6e7e",
            city: ["\u53f0\u5317\u53bf", "\u5b9c\u5170\u53bf", "\u6843\u56ed\u53bf", "\u65b0\u7af9\u53bf", "\u82d7\u6817\u53bf", "\u53f0\u4e2d\u53bf", "\u5f70\u5316\u53bf", "\u5357\u6295\u53bf", "\u4e91\u6797\u53bf", "\u5609\u4e49\u53bf", "\u53f0\u5357\u53bf", "\u9ad8\u96c4\u53bf", "\u5c4f\u4e1c\u53bf", "\u53f0\u4e1c\u53bf", "\u82b1\u83b2\u53bf", "\u6f8e\u6e56\u53bf", "\u57fa\u9686\u5e02", "\u65b0\u7af9\u5e02", "\u53f0\u4e2d\u5e02", "\u5609\u4e49\u5e02", "\u53f0\u5357\u5e02", "\u53f0\u5317\u5e02", "\u9ad8\u96c4\u5e02", "\u91d1\u95e8\u53bf", "\u8fde\u6c5f\u53bf"]
        }, {name: "\u9999\u6e2f", city: ["\u9999\u6e2f\u5c9b", "\u4e5d\u9f99", "\u65b0\u754c"]}, {
            name: "\u6fb3\u95e8",
            city: ["\u6fb3\u95e8\u534a\u5c9b", "\u6fb3\u95e8\u79bb\u5c9b"]
        }],
        end: 0
    }), Searchweb.Widget.Area = i
}(jQuery);