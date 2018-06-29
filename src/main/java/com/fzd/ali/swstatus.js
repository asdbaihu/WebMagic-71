var statusCode;

// 查询纳税人登记信息
function checkRegistryInfo() {
    /*
     * var nasrswjgLi = document.getElementById("nsrswjgLi"); if(nasrswjgLi){
     * return; }
     */
    // 纳税人识别号
    window.clearInterval(dsq);
    var nsrsbh = $("#nsrsbh").val();

    /*
     * $("#selectValuexx ul").empty(); $("#selectValuexx1 ul").empty();
     * $("#dsswjgSelect").empty(); $("#nsrsfLi ul").empty();
     * $("#kqdssfswjgSelect").empty();
     */

    // 纳税人身份
    $("#dssdnsrswjgLi ul").empty();
    $("#dssdnsrswjgLi textarea").empty();
    $("#dssdnsrswjgLi textarea").append("<option>请选择身份信息</option>");
    $("#selectValue1").val("");

    // $("#dssdnsrswjgLi textarea").append("<option>请选择身份信息</option>");
    $("#nsrswjgLi ul").empty();
    $("#nsrswjgLi textarea").empty();
    $("#nsrswjgLi textarea").append("<option>请选择国税主管税务机关</option>");
    $("#selectValue2").val("");

    $("#dsnsrswjgLi ul").empty();

    $("#dsnsrswjgLi textarea").empty();
    $("#dsnsrswjgLi textarea").append("<option>请选择地税主管税务机关</option>");
    $("#selectValue3").val("");

    // 纳税人身份
    $("#nsrsfLi textarea").empty();
    $("#nsrsfLi ul").empty();
    $("#nsrsfLi textarea").append("<option>请选择纳税人身份信息</option>");
    $("#selectValue5").val("");

    // $("#kqdssdnsrswjgLi textarea").append("<option>请选择身份信息</option>");

    $("#kqdssdnsrswjgLi textarea").empty();
    $("#kqdssdnsrswjgLi ul").empty();
    $("#kqdssdnsrswjgLi textarea").append("<option>请选择纳税人身份信息</option>");
    $("#selectValue4").val("");

    $("#dssdnsrswjgLi").css("display", "none");
    $("#nsrswjgLi").css("display", "none");
    $("#nsrsfLi").css("display", "none");
    $("#dsnsrswjgLi").css("display", "none");
    $("#captchCodeLi").css("display", "none");
    $("#nsrloginBtnLi").css("display", "none");
    $("#kqdssdnsrswjgLi").css("display", "none");
    $('#captchCode').val("");
    userxxObjec = {};

    if (nsrsbh == null || nsrsbh == "") {
        alert("请输入纳税人识别号");
        return;
    }
    var index = layer.load(0, {
        shade: 0.2
    });
    var nsrsbh = $("#nsrsbh").val();
    $.post("/sso/loginsc/queryUserxx.do?userName=" + nsrsbh, {}, function (nsrjbxx) {
        var cwxx = nsrjbxx.cwxx;
        layer.close(index);
        statusCode = nsrjbxx.statusCode;
        var dsdghbz = nsrjbxx.dsdghbz;
        var gsdghbz = nsrjbxx.gsdghbz;
        var dsckjywr = nsrjbxx.dsckjywr;
        var isshxydm = nsrjbxx.isshxydm;
        userxxObjec["dsdghbz"] = dsdghbz;
        userxxObjec["gsdghbz"] = gsdghbz;
        userxxObjec["isshxydm"] = isshxydm;
        userxxObjec["dsckjywr"] = dsckjywr;
        var sfcgbz = nsrjbxx.sfcgbz;
        if (!sfcgbz) {
            alert("未获取数据信息");
            return;
        }

        if (cwxx) {
            alert("请走新手套餐流程！");
            return;
        }

        var gsnsrList = nsrjbxx.gsnsrList;
        dsnsrjbList = nsrjbxx.dsnsrjbList;
        wbuserxxList = nsrjbxx.wbuserxxList;
        dsnsrList = nsrjbxx.dsnsrList;
        var gsdjxxbz = nsrjbxx.gsdjxxbz
        var dswtyhbz = nsrjbxx.dswtyhbz
        var xxlybz = nsrjbxx.xxlybz
        var dsdjxxbz = nsrjbxx.dsdjxxbz;

        userxxObjec["statusCode"] = statusCode;
        // 页面显示地税3种身份
        if (statusCode == '01') {
            // 地税用户信息

            if (dsnsrjbList.length > 1) {// 判断是否多天登记信息
                var swjgoptionContent = "";
                for (var i = 0; i < dsnsrjbList.length; i++) {
                    var sfmc;
                    var jsonString = JSON
                        .stringify(dsnsrjbList[i]);
                    if (dsnsrjbList[i].zyhbz == 'Y') {
                        sfmc = '主用户';
                    } else {
                        if (dsnsrjbList[i].jyhlx == '02') {
                            sfmc = '大企业业务';
                        } else if (dsnsrjbList[i].jyhlx == '03') {
                            sfmc = '个人所得税业务';
                        }
                    }

                    swjgoptionContent += "<li value='"
                        + dsnsrjbList[i].yhmc + "' data='"
                        + jsonString + "' id='dssfxx'>"
                        + dsnsrjbList[i].yhmc
                        + "&nbsp;&nbsp;"
                        + dsnsrjbList[i].mrsj + "</li> ";
                }
                $("#dssdnsrswjgLi ul")
                    .append(swjgoptionContent);
                $("#dssdnsrswjgLi").css("display", "block");

            } else {// 一条信息
                var sfmc;

                if (dsnsrjbList[0].zyhbz == 'Y') {
                    sfmc = '主用户';
                } else {
                    if (dsnsrjbList[0].jyhlx == '02') {
                        sfmc = '大企业业务';
                    } else if (dsnsrjbList[0].jyhlx == '03') {
                        sfmc = '个人所得税业务';
                    }
                }

                userxxObjec["dsyhmc"] = dsnsrjbList[0].yhmc;
                userxxObjec["dsmrsj"] = dsnsrjbList[0].mrsj;
                userxxObjec["dsjyhlx"] = dsnsrjbList[0].jyhlx;// 旧用户类型
                userxxObjec["dsnsrsbh"] = dsnsrjbList[0].nsrsbh;
                userxxObjec["dsshxydm"] = dsnsrjbList[0].shxydm;
                userxxObjec["dszsyhbz"] = dsnsrjbList[0].zsyhbz;
                userxxObjec["dsyhlx"] = dsnsrjbList[0].yhlx; // 用户来源
                userxxObjec["dsyhId"] = dsnsrjbList[0].yhId;
                userxxObjec["statusCode"] = statusCode;
                userxxObjec["dsyhuuid"] = dsnsrjbList[0].uuid;
                userxxObjec["dsid"] = dsnsrjbList[0].id;
                if (dsnsrList.length > 1) {
                    var dsdjxxoption = "";
                    for (var i = 0; i < dsnsrList.length; i++) {
                        var jsonString = JSON
                            .stringify(dsnsrList[i]);

                        var swjgjbxx = dsnsrList[i].nsrsbh
                            + "&nbsp;&nbsp;状态："
                            + dsnsrList[i].nsrztmc
                            + "&nbsp;&nbsp;登记时间："
                            + dsnsrList[i].djrq
                            + "&nbsp;&nbsp;"
                            + dsnsrList[i].zgswskfjmc;
                        dsdjxxoption += "<li value='"
                            + dsnsrList[i].djxh
                            + "' data='" + jsonString
                            + "' id='dsdjxx'>" + swjgjbxx
                            + "</li> ";
                    }
                    $("#dsnsrswjgLi ul").append(dsdjxxoption);

                    $("#dsnsrswjgLi").css("display", "block");

                } else {
                    var nsrzt = "";
                    var kdqcc = "";
                    var yxbz = "";
                    if ((dsnsrList[0].nsrzt_dm != '01'
                        && dsnsrList[0].nsrzt_dm != '02'
                        && dsnsrList[0].nsrzt_dm != '03'
                        && dsnsrList[0].nsrzt_dm != '04'
                        && dsnsrList[0].nsrzt_dm != '06' && dsnsrList[0].nsrzt_dm != '09')
                        || dsnsrList[0].yxbz != 'Y') {

                        if (dsnsrList[0].nsrzt_dm != '01'
                            && dsnsrList[0].nsrzt_dm != '02'
                            && dsnsrList[0].nsrzt_dm != '03'
                            && dsnsrList[0].nsrzt_dm != '04'
                            && dsnsrList[0].nsrzt_dm != '06'
                            && dsnsrList[0].nsrzt_dm != '09') {
                            nsrzt = "纳税人状态为"
                                + dsnsrList[0].nsrztmc
                                + ",";
                        }
                        if (dsnsrList[0].yxbz != 'Y') {
                            yxbz = "有效标志为 N ,";
                        }

                        alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc
                            + yxbz
                            + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

                        return;

                    }
                    userxxObjec["dszgswj_dm"] = dsnsrList[0].zgswj_dm;
                    userxxObjec["dsgdghlx_dm"] = dsnsrList[0].gdghlx_dm;
                    userxxObjec["dsuuid"] = dsnsrList[0].uuid
                    userxxObjec["dsnsrmc"] = dsnsrList[0].nsrmc;
                    userxxObjec["dsnsrsbh"] = dsnsrList[0].nsrsbh;
                    userxxObjec["dsdjxh"] = dsnsrList[0].djxh;
                    userxxObjec["dsdjzclx_dm"] = dsnsrList[0].djzclx_dm;
                    userxxObjec["dszgswskfj_dm"] = dsnsrList[0].zgswskfj_dm;
                    userxxObjec["dsssdabh"] = dsnsrList[0].ssdabh;
                    userxxObjec["dsnsrzt_dm"] = dsnsrList[0].nsrzt_dm;
                    userxxObjec["dskzztdjlx_dm"] = dsnsrList[0].kzztdjlx_dm;
                    userxxObjec["dsshxydm"] = dsnsrList[0].shxydm;
                    var swjgoptionContent = "";
                    var jsonString = JSON
                        .stringify(dsnsrjbList[0]);
                    swjgoptionContent += "<li value='"
                        + dsnsrjbList[0].yhmc
                        + "' data='"
                        + jsonString
                        + "' selected='selected' id='dssfxx'>"
                        + dsnsrjbList[0].yhmc
                        + "&nbsp;&nbsp;"
                        + dsnsrjbList[0].mrsj + "</li> ";
                    $("#dssdnsrswjgLi ul").append(
                        swjgoptionContent);
                    $("#dssdnsrswjgLi").css("display", "block");

                    $("#captchCodeLi").css("display", "block");
                    $("#nsrloginBtnLi").css("display", "block");
                }

            }

        }

        //
        if (statusCode == '02') {
            alert("请走新手套餐流程");
            return;
        }
        // 国税地税都无登记，工商无新开业信息
        if (statusCode == '03') {
            alert("你输入的纳税人识别号不正确或者你登记信息有误，请重新输入或者到实体大厅办理相关业务");
            return;
        }
        // 页面显示国税登记信息，选择后显示经办人信息
        if (statusCode == '04') {
            userxxObjec["statusCode"] = statusCode;
            if (gsnsrList.length == '1'
                && dsnsrList.length == '1') {

                var nsrzt = "";
                var kdqcc = "";
                var yxbz = "";
                if (gsnsrList[0].kqccsztdjbz != 'N'
                    || (gsnsrList[0].nsrztDm != '01'
                        && gsnsrList[0].nsrztDm != '02'
                        && gsnsrList[0].nsrztDm != '03'
                        && gsnsrList[0].nsrztDm != '04'
                        && gsnsrList[0].nsrztDm != '06' && gsnsrList[0].nsrztDm != '09')
                    || gsnsrList[0].yxbz != 'Y') {

                    if (gsnsrList[0].kqccsztdjbz != 'N') {
                        kdqcc = "跨区财产主体登记为 Y,";
                    }
                    if (gsnsrList[0].nsrztDm != '01'
                        && gsnsrList[0].nsrztDm != '02'
                        && gsnsrList[0].nsrztDm != '03'
                        && gsnsrList[0].nsrztDm != '04'
                        && gsnsrList[0].nsrztDm != '06'
                        && gsnsrList[0].nsrztDm != '09') {
                        nsrzt = "纳税人状态为" + gsnsrList[0].nsrztmc
                            + ",";
                    }
                    if (gsnsrList[0].yxbz != 'Y') {
                        yxbz = "有效标志为 N ,";
                    }

                    alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc
                        + yxbz + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

                    return;

                }
                userxxObjec["gsuuid"] = gsnsrList[0].uuid;
                userxxObjec["gsdjxh"] = gsnsrList[0].djxh;
                userxxObjec["gsnsrsbh"] = gsnsrList[0].nsrsbh;
                userxxObjec["gslrrDm "] = gsnsrList[0].lrrDm;
                userxxObjec["gsdjxh"] = gsnsrList[0].djxh;
                userxxObjec["gsssdabh"] = gsnsrList[0].ssdabh;
                userxxObjec["gsnsrmc"] = gsnsrList[0].nsrmc;
                userxxObjec["gskzztdjlxDm"] = gsnsrList[0].kzztdjlxDm;
                userxxObjec["gsdjzclxDm"] = gsnsrList[0].djzclxDm;
                userxxObjec["gszgswskfjDm"] = gsnsrList[0].zgswskfjDm;
                userxxObjec["gsnsrztDm"] = gsnsrList[0].nsrztDm;
                userxxObjec["gsgdghlxDm"] = gsnsrList[0].gdghlxDm;
                userxxObjec["gsshxydm"] = gsnsrList[0].shxydm;
                userxxObjec["gszgswjgDm"] = gsnsrList[0].zgswjgDm;

                if (dsnsrList[0].kqccsztdjbz != 'N') {
                    userxxObjec["dsuuid"] = dsnsrList[0].uuid

                    userxxObjec["dsnsrmc"] = dsnsrList[0].nsrmc;
                    userxxObjec["dszgswj_dm"] = dsnsrList[0].zgswj_dm;
                    userxxObjec["dsgdghlx_dm"] = dsnsrList[0].gdghlx_dm;
                    userxxObjec["dsnsrsbh"] = dsnsrList[0].nsrsbh;
                    userxxObjec["dsdjxh"] = dsnsrList[0].djxh;
                    userxxObjec["dsdjzclx_dm"] = dsnsrList[0].djzclx_dm;
                    userxxObjec["dszgswskfj_dm"] = dsnsrList[0].zgswskfj_dm;
                    userxxObjec["dsssdabh"] = dsnsrList[0].ssdabh;
                    userxxObjec["dsnsrzt_dm"] = dsnsrList[0].nsrzt_dm;
                    userxxObjec["dskzztdjlx_dm"] = dsnsrList[0].kzztdjlx_dm;
                    userxxObjec["dsshxydm"] = dsnsrList[0].shxydm;
                    if (dsnsrjbList.length > 0) {// 判断是否多天登记信息
                        var swjgoptionContent = "";
                        for (var i = 0; i < dsnsrjbList.length; i++) {
                            var sfmc;
                            var jsonString = JSON
                                .stringify(dsnsrjbList[i]);
                            if (dsnsrjbList[i].zyhbz == 'Y') {
                                sfmc = '主用户';
                            } else {
                                if (dsnsrjbList[i].jyhlx == '02') {
                                    sfmc = '大企业业务';
                                } else if (dsnsrjbList[i].jyhlx == '03') {
                                    sfmc = '个人所得税业务';
                                }
                            }

                            swjgoptionContent += "<li value='kj' bz='kj' data='"
                                + jsonString
                                + "' id='kjdsdjxx' >"
                                + dsnsrjbList[i].yhmc
                                + "&nbsp;&nbsp;"
                                + dsnsrjbList[i].mrsj
                                + "&nbsp;&nbsp;"
                                + sfmc
                                + "</li> ";
                        }
                        $("#kqdssdnsrswjgLi ul").append(
                            swjgoptionContent);
                        $("#kqdssdnsrswjgLi").css("display",
                            "block");
                    }
                } else {

                    if (gsnsrList[0].ssdabh == dsnsrList[0].ssdabh) {
                        if (gsnsrList[0].nsrztDm == dsnsrList[0].nsrzt_dm
                            || gsnsrList[0].nsrztDm == '06'
                            || dsnsrList[0].nsrzt_dm == '06') {

                            if (dsnsrList[0].nsrzt_dm != '01'
                                && dsnsrList[0].nsrzt_dm != '02'
                                && dsnsrList[0].nsrzt_dm != '03'
                                && dsnsrList[0].nsrzt_dm != '04'
                                && dsnsrList[0].nsrzt_dm != '06'
                                && dsnsrList[0].nsrzt_dm != '09') {
                                alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                                return;
                            }

                            userxxObjec["dsuuid"] = dsnsrList[0].uuid
                            userxxObjec["dsnsrmc"] = dsnsrList[0].nsrmc;
                            userxxObjec["dszgswj_dm"] = dsnsrList[0].zgswj_dm;
                            userxxObjec["dsgdghlx_dm"] = dsnsrList[0].gdghlx_dm;
                            userxxObjec["dsscjydz"] = dsnsrList[0].scjydz;
                            userxxObjec["dsnsrsbh"] = dsnsrList[0].nsrsbh;
                            userxxObjec["dsdjxh"] = dsnsrList[0].djxh;
                            userxxObjec["dsdjzclx_dm"] = dsnsrList[0].djzclx_dm;
                            userxxObjec["dszgswskfj_dm"] = dsnsrList[0].zgswskfj_dm;
                            userxxObjec["dsssdabh"] = dsnsrList[0].ssdabh;
                            userxxObjec["dsnsrzt_dm"] = dsnsrList[0].nsrzt_dm;
                            userxxObjec["dskzztdjlx_dm"] = dsnsrList[0].kzztdjlx_dm;
                            userxxObjec["dsshxydm"] = dsnsrList[0].shxydm;
                            var sfxxconoption = "";
                            var sfxxlist = gsnsrList[0].sfxxlist
                            for (var i = 0; i < sfxxlist.length; i++) {
                                var jsonString = JSON
                                    .stringify(sfxxlist[i]);
                                sfxxconoption += "<li value='gs' bz='gs'  data='"
                                    + jsonString
                                    + "' id='nsrsfxx'>"
                                    + sfxxlist[i].xm
                                    + "&nbsp;&nbsp;"
                                    + sfxxlist[i].sjhm
                                    + "&nbsp;&nbsp;"
                                    + sfxxlist[i].sflx
                                    + "</li> ";
                            }
                            for (var i = 0; i < dsnsrjbList.length; i++) {
                                var jsonString = JSON
                                    .stringify(dsnsrjbList[i]);
                                if (dsnsrjbList[i].zyhbz == 'N') {
                                    sfxxconoption += "<li value='ds' bz='ds' data='"
                                        + jsonString
                                        + "' id='nsrsfxx'>"
                                        + dsnsrjbList[i].yhmc
                                        + "&nbsp;&nbsp;"
                                        + dsnsrjbList[i].mrsj
                                        + "&nbsp;&nbsp;</li> ";
                                }
                            }
                            $("#nsrsfLi ul").append(
                                sfxxconoption);
                            $("#nsrsfLi").css("display",
                                "block");
                            return;
                        } else {
                            alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                            return;
                        }

                    } else {
                        alert("暂无法建立国税登记信息与地税登记信息关联关系。请重新选择，或到实体大厅办理“国地税对照关系管理”业务");
                        return;
                    }
                }
            }

            if (gsnsrList.length > 0) {// 判断是否多天登记信息
                var swjgoptionContent = "";
                for (var i = 0; i < gsnsrList.length; i++) {

                    var jsonString = JSON
                        .stringify(gsnsrList[i]);

                    swjgoptionContent += "<li value='"
                        + gsnsrList[i].djxh + "' data='"
                        + jsonString + "' id ='gsdjxx'>"
                        + gsnsrList[i].nsrmc
                        + "&nbsp;&nbsp;状态："
                        + gsnsrList[i].nsrztmc
                        + "&nbsp;&nbsp;登记时间："
                        + gsnsrList[i].djrq
                        + "&nbsp;&nbsp;"
                        + gsnsrList[i].zgswskfjmc
                        + "</li> ";
                }
                $("#nsrswjgLi ul").append(swjgoptionContent);
                $("#nsrswjgLi").css("display", "block");

            }
            if (dsnsrList.length > 0) {// 判断地税登记信息
                var dsswjgoptionContent = "";
                for (var i = 0; i < dsnsrList.length; i++) {

                    var jsonString = JSON
                        .stringify(dsnsrList[i]);

                    dsswjgoptionContent += "<li value='"
                        + dsnsrList[i].djxh + "' data='"
                        + jsonString + "' id='dsdjxx'>"
                        + dsnsrList[i].nsrmc
                        + "&nbsp;&nbsp;状态："
                        + dsnsrList[i].nsrztmc
                        + "&nbsp;&nbsp;登记时间："
                        + dsnsrList[i].djrq
                        + "&nbsp;&nbsp;"
                        + dsnsrList[i].zgswskfjmc
                        + "</li> ";
                }
                $("#dsnsrswjgLi ul")
                    .append(dsswjgoptionContent);
                $("#dsnsrswjgLi").css("display", "block");
            }
        }
        //
        if (statusCode == '05') {

            if (gsnsrList.length == '1'
                && dsnsrList.length == '1') {
                var nsrzt = "";
                var kdqcc = "";
                var yxbz = "";
                if (gsnsrList[0].kqccsztdjbz != 'N'
                    || (gsnsrList[0].nsrztDm != '01'
                        && gsnsrList[0].nsrztDm != '02'
                        && gsnsrList[0].nsrztDm != '03'
                        && gsnsrList[0].nsrztDm != '04'
                        && gsnsrList[0].nsrztDm != '06' && gsnsrList[0].nsrztDm != '09')
                    || gsnsrList[0].yxbz != 'Y') {

                    if (gsnsrList[0].kqccsztdjbz != 'N') {
                        kdqcc = "跨区财产主体登记为 Y,";
                    }
                    if (gsnsrList[0].nsrztDm != '01'
                        && gsnsrList[0].nsrztDm != '02'
                        && gsnsrList[0].nsrztDm != '03'
                        && gsnsrList[0].nsrztDm != '04'
                        && gsnsrList[0].nsrztDm != '06'
                        && gsnsrList[0].nsrztDm != '09') {
                        nsrzt = "纳税人状态为" + gsnsrList[0].nsrztmc
                            + ",";
                    }
                    if (gsnsrList[0].yxbz != 'Y') {
                        yxbz = "有效标志为 N ,";
                    }

                    alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc
                        + yxbz + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

                    return;

                }

                userxxObjec["gsuuid"] = gsnsrList[0].uuid
                userxxObjec["gsdjxh"] = gsnsrList[0].djxh;
                userxxObjec["gsnsrsbh"] = gsnsrList[0].nsrsbh;
                userxxObjec["gsdjxh"] = gsnsrList[0].djxh;
                userxxObjec["gsssdabh"] = gsnsrList[0].ssdabh;
                userxxObjec["gsnsrmc"] = gsnsrList[0].nsrmc;

                userxxObjec["gskzztdjlxDm"] = gsnsrList[0].kzztdjlxDm;
                userxxObjec["gsdjzclxDm"] = gsnsrList[0].djzclxDm;
                userxxObjec["gszgswskfjDm"] = gsnsrList[0].zgswskfjDm;
                userxxObjec["gsnsrztDm"] = gsnsrList[0].nsrztDm;
                userxxObjec["gsgdghlxDm"] = gsnsrList[0].gdghlxDm;
                userxxObjec["gsshxydm"] = gsnsrList[0].shxydm;
                userxxObjec["gszgswjgDm"] = gsnsrList[0].zgswjgDm;
                if (dsnsrList[0].kqccsztdjbz != 'N') {
                    userxxObjec["dsuuid"] = dsnsrList[0].uuid

                    userxxObjec["dsnsrmc"] = dsnsrList[0].nsrmc;
                    userxxObjec["dszgswj_dm"] = dsnsrList[0].zgswj_dm;
                    userxxObjec["dsgdghlx_dm"] = dsnsrList[0].gdghlx_dm;
                    userxxObjec["dsnsrsbh"] = dsnsrList[0].nsrsbh;
                    userxxObjec["dsdjxh"] = dsnsrList[0].djxh;
                    userxxObjec["dsdjzclx_dm"] = dsnsrList[0].djzclx_dm;
                    userxxObjec["dszgswskfj_dm"] = dsnsrList[0].zgswskfj_dm;
                    userxxObjec["dsssdabh"] = dsnsrList[0].ssdabh;
                    userxxObjec["dsnsrzt_dm"] = dsnsrList[0].nsrzt_dm;
                    userxxObjec["dskzztdjlx_dm"] = dsnsrList[0].kzztdjlx_dm;
                    userxxObjec["dsshxydm"] = dsnsrList[0].shxydm;
                    if (dsnsrjbList.length > 0) {// 判断是否多天登记信息
                        var swjgoptionContent = "";
                        for (var i = 0; i < dsnsrjbList.length; i++) {
                            var sfmc;
                            var jsonString = JSON
                                .stringify(dsnsrjbList[i]);
                            if (dsnsrjbList[i].zyhbz == 'Y') {
                                sfmc = '主用户';
                            } else {
                                if (dsnsrjbList[i].jyhlx == '02') {
                                    sfmc = '大企业业务';
                                } else if (dsnsrjbList[i].jyhlx == '03') {
                                    sfmc = '个人所得税业务';
                                }
                            }

                            swjgoptionContent += "<li value='kj' bz='kj' data='"
                                + jsonString
                                + "' id='kjdsdjxx'>"
                                + dsnsrjbList[i].yhmc
                                + "&nbsp;&nbsp;"
                                + dsnsrjbList[i].mrsj
                                + "&nbsp;&nbsp;"
                                + sfmc
                                + "</li> ";
                        }
                        $("#kqdssdnsrswjgLi ul").append(
                            swjgoptionContent);
                        $("#kqdssdnsrswjgLi").css("display",
                            "block");
                    }
                } else {

                    if (gsnsrList[0].ssdabh == dsnsrList[0].ssdabh) {
                        if (gsnsrList[0].nsrztDm == dsnsrList[0].nsrzt_dm
                            || gsnsrList[0].nsrztDm == '06'
                            || dsnsrList[0].nsrzt_dm == '06') {

                            if (dsnsrList[0].nsrzt_dm != '01'
                                && dsnsrList[0].nsrzt_dm != '02'
                                && dsnsrList[0].nsrzt_dm != '03'
                                && dsnsrList[0].nsrzt_dm != '04'
                                && dsnsrList[0].nsrzt_dm != '06'
                                && dsnsrList[0].nsrzt_dm != '09') {
                                alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                                return;
                            }
                            userxxObjec["dsuuid"] = dsnsrList[0].uuid
                            userxxObjec["dsnsrmc"] = dsnsrList[0].nsrmc;
                            userxxObjec["dszgswj_dm"] = dsnsrList[0].zgswj_dm;
                            userxxObjec["dsgdghlx_dm"] = dsnsrList[0].gdghlx_dm;
                            userxxObjec["dsnsrsbh"] = dsnsrList[0].nsrsbh;
                            userxxObjec["dsdjxh"] = dsnsrList[0].djxh;
                            userxxObjec["dsdjzclx_dm"] = dsnsrList[0].djzclx_dm;
                            userxxObjec["dszgswskfj_dm"] = dsnsrList[0].zgswskfj_dm;
                            userxxObjec["dsssdabh"] = dsnsrList[0].ssdabh;
                            userxxObjec["dsnsrzt_dm"] = dsnsrList[0].nsrzt_dm;
                            userxxObjec["dskzztdjlx_dm"] = dsnsrList[0].kzztdjlx_dm;
                            userxxObjec["dsshxydm"] = dsnsrList[0].shxydm;
                            var sfxxconoption = "";
                            var sfxxlist = gsnsrList[0].sfxxlist
                            for (var i = 0; i < sfxxlist.length; i++) {
                                var jsonString = JSON
                                    .stringify(sfxxlist[i]);
                                sfxxconoption += "<li value='"
                                    + sfxxlist[i].sjhm
                                    + "' data='"
                                    + jsonString
                                    + "' id='nsrsfxx'>"
                                    + sfxxlist[i].xm
                                    + "&nbsp;&nbsp;"
                                    + sfxxlist[i].sjhm
                                    + "&nbsp;&nbsp;"
                                    + sfxxlist[i].sflx
                                    + "</li> ";
                            }
                            $("#nsrsfLi ul").append(
                                sfxxconoption);
                            $("#nsrsfLi").css("display",
                                "block");
                            return;
                        } else {
                            alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                            return;
                        }

                    } else {
                        alert("暂无法建立国税登记信息与地税登记信息关联关系。请重新选择，或到实体大厅办理“国地税对照关系管理”业务");
                        return;
                    }

                }

            }

            if (gsnsrList.length > 0) {// 判断是否多天登记信息
                var swjgoptionContent = "";
                for (var i = 0; i < gsnsrList.length; i++) {

                    var jsonString = JSON
                        .stringify(gsnsrList[i]);

                    swjgoptionContent += "<li value='"
                        + gsnsrList[i].djxh + "' data='"
                        + jsonString + "'  id='gsdjxx'>"
                        + gsnsrList[i].nsrmc
                        + "&nbsp;&nbsp;状态："
                        + gsnsrList[i].nsrztmc
                        + "&nbsp;&nbsp;登记时间："
                        + gsnsrList[i].djrq
                        + "&nbsp;&nbsp;"
                        + gsnsrList[i].zgswskfjmc
                        + "</li> ";
                }

                $("#nsrswjgLi ul").append(swjgoptionContent);
                $("#nsrswjgLi").css("display", "block");

            }
            if (dsnsrList.length > 0) {// 判断地税登记信息
                var dsswjgoptionContent = "";
                for (var i = 0; i < dsnsrList.length; i++) {

                    var jsonString = JSON
                        .stringify(dsnsrList[i]);

                    dsswjgoptionContent += "<li value='"
                        + dsnsrList[i].djxh + "' data='"
                        + jsonString + "' id='dsdjxx'>"
                        + dsnsrList[i].nsrmc
                        + "&nbsp;&nbsp;状态："
                        + dsnsrList[i].nsrztmc
                        + "&nbsp;&nbsp;登记时间："
                        + dsnsrList[i].djrq
                        + "&nbsp;&nbsp;"
                        + dsnsrList[i].zgswskfjmc
                        + "</li> ";
                }
                $("#dsnsrswjgLi ul")
                    .append(dsswjgoptionContent);
                $("#dsnsrswjgLi").css("display", "block");
            }

        }
        //
        if (statusCode == '06') {

            if (gsnsrList.length > 1) {// 判断是否多天登记信息
                var swjgoptionContent = "";
                for (var i = 0; i < gsnsrList.length; i++) {

                    var jsonString = JSON
                        .stringify(gsnsrList[i]);

                    swjgoptionContent += "<li value='"
                        + gsnsrList[i].djxh + "' data='"
                        + jsonString + "' id='gsdjxx'>"
                        + gsnsrList[i].nsrmc
                        + "&nbsp;&nbsp;状态："
                        + gsnsrList[i].nsrztmc
                        + "&nbsp;&nbsp;登记时间："
                        + gsnsrList[i].djrq
                        + "&nbsp;&nbsp;"
                        + gsnsrList[i].zgswskfjmc
                        + "</li> ";
                }
                $("#nsrswjgLi ul").append(swjgoptionContent);
                $("#nsrswjgLi").css("display", "block");

            } else {
                var nsrzt = "";
                var kdqcc = "";
                var yxbz = "";
                if (gsnsrList[0].kqccsztdjbz != 'N'
                    || (gsnsrList[0].nsrztDm != '01'
                        && gsnsrList[0].nsrztDm != '02'
                        && gsnsrList[0].nsrztDm != '03'
                        && gsnsrList[0].nsrztDm != '04'
                        && gsnsrList[0].nsrztDm != '06' && gsnsrList[0].nsrztDm != '09')
                    || gsnsrList[0].yxbz != 'Y') {

                    if (gsnsrList[0].kqccsztdjbz != 'N') {
                        kdqcc = "跨区财产主体登记为 Y,";
                    }
                    if (gsnsrList[0].nsrztDm != '01'
                        && gsnsrList[0].nsrztDm != '02'
                        && gsnsrList[0].nsrztDm != '03'
                        && gsnsrList[0].nsrztDm != '04'
                        && gsnsrList[0].nsrztDm != '06'
                        && gsnsrList[0].nsrztDm != '09') {
                        nsrzt = "纳税人状态为" + gsnsrList[0].nsrztmc
                            + ",";
                    }
                    if (gsnsrList[0].yxbz != 'Y') {
                        yxbz = "有效标志为 N ,";
                    }

                    alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc
                        + yxbz + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

                    return;

                }

                userxxObjec["gsuuid"] = gsnsrList[0].uuid
                userxxObjec["gsdjxh"] = gsnsrList[0].djsxh;
                userxxObjec["gsnsrsbh"] = gsnsrList[0].nsrsbh;
                userxxObjec["gsdjxh"] = gsnsrList[0].djxh;
                userxxObjec["gsssdabh"] = gsnsrList[0].ssdabh;
                userxxObjec["gsnsrmc"] = gsnsrList[0].nsrmc;
                userxxObjec["gskzztdjlxDm"] = gsnsrList[0].kzztdjlxDm;
                userxxObjec["gsdjzclxDm"] = gsnsrList[0].djzclxDm;
                userxxObjec["gszgswskfjDm"] = gsnsrList[0].zgswskfjDm;
                userxxObjec["gsnsrztDm"] = gsnsrList[0].nsrztDm;
                userxxObjec["gsgdghlxDm"] = gsnsrList[0].gdghlxDm;
                userxxObjec["gsshxydm"] = gsnsrList[0].shxydm;
                userxxObjec["gszgswjgDm"] = gsnsrList[0].zgswjgDm;
                // 显示身份信息
                var sfxxlist = gsnsrList[0].sfxxlist;
                if (sfxxlist.length > 0) {

                    var sfxxoptionContent = "";
                    for (var i = 0; i < sfxxlist.length; i++) {
                        var jsonString = JSON
                            .stringify(sfxxlist[i]);
                        sfxxoptionContent += "<li value='"
                            + sfxxlist[i].sjhm + "' data='"
                            + jsonString
                            + "' id='nsrsfxx'>"
                            + sfxxlist[i].xm
                            + "&nbsp;&nbsp;"
                            + sfxxlist[i].sjhm
                            + "&nbsp;&nbsp;"
                            + sfxxlist[i].sflx + "</li> ";
                    }

                    $("#nsrsfLi ul").append(sfxxoptionContent);

                    $("#nsrsfLi").css("display", "block");

                } else {
                    alert("无身份信息!请核实数据");
                    return;
                }

            }

        }

    }).error(function () {
        alert("获取信息错误，请联系管理员！");
        layer.close(index);
        return;
    });

}

function dssfxxselectSwjg(value, data) {
    $("#dsnsrswjgLi ul").empty();
    $("#dsnsrswjgLi textarea").empty();
    $("#dsnsrswjgLi textarea").append("<option>请选择地税主管税务机关</option>");
    // $("#dsnsrswjgLi ul").empty();
    $("#selectValue3").val("");
    // var data=$('#dssfswjgSelect option:selected').attr('data');
    var json = $.parseJSON(data);
    if (statusCode == '01') {
        userxxObjec["dsyhmc"] = json.yhmc;
        userxxObjec["dsmrsj"] = json.mrsj;
        userxxObjec["dsjyhlx"] = json.jyhlx;// 旧用户类型
        userxxObjec["dsnsrsbh"] = json.nsrsbh;
        userxxObjec["dsshxydm"] = json.shxydm;
        userxxObjec["dszsyhbz"] = json.zsyhbz;
        userxxObjec["dsyhlx"] = json.yhlx; // 用户来源
        userxxObjec["dsyhId"] = json.yhId;
        userxxObjec["statusCode"] = statusCode;
        userxxObjec["dsyhuuid"] = json.uuid;
        userxxObjec["dsid"] = json.id;
        if (dsnsrList.length > 0) {
            var dsdjxxoption = ""
            for (var i = 0; i < dsnsrList.length; i++) {
                var jsonString = JSON.stringify(dsnsrList[i]);
                dsdjxxoption += "<li value='" + dsnsrList[i].djxh + "' data='"
                    + jsonString + "' id='dsdjxx'>" + dsnsrList[i].nsrmc
                    + "&nbsp;&nbsp;状态：" + dsnsrList[i].nsrztmc
                    + "&nbsp;&nbsp;登记时间：" + dsnsrList[i].djrq
                    + "&nbsp;&nbsp;" + dsnsrList[i].zgswskfjmc + "</li> ";
            }
            $("#dsnsrswjgLi ul").append(dsdjxxoption);

            $("#dsnsrswjgLi").css("display", "block");

        }

    }

}

function dsselectSwjg(value, data) {
    $("#nsrsfLi textarea").empty();
    $("#nsrsfLi textarea").append("<option>请选择纳税人身份信息</option>");
    $("#nsrsfLi").css("display", "none");
    $("#nsrsfLi ul").empty();

    $("#kqdssdnsrswjgLi ul").empty();
    $("#kqdssdnsrswjgLi textarea").empty();
    $("#kqdssdnsrswjgLi textarea").append("<option>请选择身份信息</option>");
    $("#kqdssdnsrswjgLi").css("display", "none");

    // 选择后登陆

    if (statusCode == '01') {

        // var data=$('#dsswjgSelect option:selected').attr('data');
        var data1 = $('#selectValue1').val();
        var json1 = $.parseJSON(data1);
        var json = $.parseJSON(data);
        var nsrzt = "";
        var kdqcc = "";
        var yxbz = "";
        if ((json.nsrzt_dm != '01' && json.nsrzt_dm != '02'
            && json.nsrzt_dm != '03' && json.nsrzt_dm != '04'
            && json.nsrzt_dm != '06' && json.nsrzt_dm != '09')
            || json.yxbz != 'Y') {

            if (json.nsrzt_dm != '01' && json.nsrzt_dm != '02'
                && json.nsrzt_dm != '03' && json.nsrzt_dm != '04'
                && json.nsrzt_dm != '06' && json.nsrzt_dm != '09') {
                nsrzt = "纳税人状态为" + json.nsrztmc + ",";
            }
            if (json.yxbz != 'Y') {
                yxbz = "有效标志为 N ,";
            }

            alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc + yxbz
                + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

            return;

        }

        userxxObjec["dsuuid"] = json.uuid
        userxxObjec["dsnsrmc"] = json.nsrmc;
        userxxObjec["dszgswj_dm"] = json.zgswj_dm;
        userxxObjec["dsgdghlx_dm"] = json.gdghlx_dm;
        userxxObjec["dsnsrsbh"] = json.nsrsbh;
        userxxObjec["dsdjxh"] = json.djxh;
        userxxObjec["dsdjzclx_dm"] = json.djzclx_dm;
        userxxObjec["dszgswskfj_dm"] = json.zgswskfj_dm;
        userxxObjec["dsssdabh"] = json.ssdabh;
        userxxObjec["dsnsrzt_dm"] = json.nsrzt_dm;
        userxxObjec["dskzztdjlx_dm"] = json.kzztdjlx_dm;
        userxxObjec["dsshxydm"] = json.shxydm;

        $("#id").val(userxxObjec.dsid);
        $("#sjhm").val(userxxObjec.dsmrsj);
        $("#captchCodeLi").css("display", "block");
        $("#nsrloginBtnLi").css("display", "block");

    }

    if (statusCode == '05') {
        var gsdata = $('#selectValue2').val();
        var gsjson = $.parseJSON(gsdata);
        // var dsdata=$('#selectValue3').val();
        var dsjson = $.parseJSON(data);
        if (null != gsjson && gsjson != "" && typeof gsjson != "undefined") {

            var nsrzt = "";
            var kdqcc = "";
            var yxbz = "";
            if ((dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
                && dsjson.nsrzt_dm != '03' && dsjson.nsrzt_dm != '04'
                && dsjson.nsrzt_dm != '06' && dsjson.nsrzt_dm != '09')
                || dsjson.yxbz != 'Y') {

                if (dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
                    && dsjson.nsrzt_dm != '03' && dsjson.nsrzt_dm != '04'
                    && dsjson.nsrzt_dm != '06' && dsjson.nsrzt_dm != '09') {
                    nsrzt = "纳税人状态为" + dsjson.nsrztmc + ",";
                }
                if (dsjson.yxbz != 'Y') {
                    yxbz = "有效标志为 N ,";
                }

                alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc + yxbz
                    + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

                return;

            }
            if (gsjson.ssdabh == dsjson.ssdabh) {

                if (dsjson.kqccsztdjbz != 'N') {
                    userxxObjec["dsuuid"] = dsjson.uuid

                    userxxObjec["dsnsrmc"] = dsjson.nsrmc;

                    userxxObjec["dsnsrsbh"] = dsjson.nsrsbh;
                    userxxObjec["dsdjxh"] = dsjson.djxh;
                    userxxObjec["dsdjzclx_dm"] = dsjson.djzclx_dm;
                    userxxObjec["dszgswskfj_dm"] = dsjson.zgswskfj_dm;
                    userxxObjec["dszgswj_dm"] = dsjson.zgswj_dm;
                    userxxObjec["dsgdghlx_dm"] = dsjson.gdghlx_dm;

                    userxxObjec["dsssdabh"] = dsjson.ssdabh;
                    userxxObjec["dsnsrzt_dm"] = dsjson.nsrzt_dm;
                    userxxObjec["dskzztdjlx_dm"] = dsjson.kzztdjlx_dm;
                    userxxObjec["dsshxydm"] = dsjson.shxydm;
                    if (dsnsrjbList.length > 0) {// 判断是否多天登记信息
                        var swjgoptionContent = "";
                        for (var i = 0; i < dsnsrjbList.length; i++) {
                            var sfmc;
                            var jsonString = JSON.stringify(dsnsrjbList[i]);
                            if (dsnsrjbList[i].zyhbz == 'Y') {
                                sfmc = '主用户';
                            } else {
                                if (dsnsrjbList[i].jyhlx == '02') {
                                    sfmc = '大企业业务';
                                } else if (dsnsrjbList[i].jyhlx == '03') {
                                    sfmc = '个人所得税业务';
                                }
                            }

                            swjgoptionContent += "<li value='kj' bz='kj' data='"
                                + jsonString
                                + "' id='kjdsdjxx'>"
                                + dsnsrjbList[i].yhmc
                                + "&nbsp;&nbsp;"
                                + dsnsrjbList[i].mrsj
                                + "&nbsp;&nbsp;"
                                + sfmc + "</li> ";
                        }
                        $("#kqdssdnsrswjgLi ul").append(swjgoptionContent);
                        $("#kqdssdnsrswjgLi").css("display", "block");
                    }
                } else {

                    if (gsjson.nsrztDm == dsjson.nsrzt_dm
                        || gsjson.nsrztDm == '06'
                        || dsjson.nsrzt_dm == '06') {
                        if (dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
                            && dsjson.nsrzt_dm != '03'
                            && dsjson.nsrzt_dm != '04'
                            && dsjson.nsrzt_dm != '06'
                            && dsjson.nsrzt_dm != '09') {
                            alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                            return;
                        }
                        userxxObjec["dsuuid"] = dsjson.uuid

                        userxxObjec["dsnsrmc"] = dsjson.nsrmc;
                        userxxObjec["dszgswj_dm"] = dsjson.zgswj_dm;
                        userxxObjec["dsgdghlx_dm"] = dsjson.gdghlx_dm;
                        userxxObjec["dsnsrsbh"] = dsjson.nsrsbh;
                        userxxObjec["dsdjxh"] = dsjson.djxh;
                        userxxObjec["dsdjzclx_dm"] = dsjson.djzclx_dm;
                        userxxObjec["dszgswskfj_dm"] = dsjson.zgswskfj_dm;
                        userxxObjec["dsssdabh"] = dsjson.ssdabh;
                        userxxObjec["dsnsrzt_dm"] = dsjson.nsrzt_dm;
                        userxxObjec["dskzztdjlx_dm"] = dsjson.kzztdjlx_dm;
                        userxxObjec["dsshxydm"] = dsjson.shxydm;
                        var sfxxconoption = "";
                        var sfxxlist = gsjson.sfxxlist
                        for (var i = 0; i < sfxxlist.length; i++) {
                            var jsonString = JSON.stringify(sfxxlist[i]);
                            sfxxconoption += "<li value='" + sfxxlist[i].sjhm
                                + "' data='" + jsonString
                                + "' id='nsrsfxx'>" + sfxxlist[i].xm
                                + "&nbsp;&nbsp;" + sfxxlist[i].sjhm
                                + "&nbsp;&nbsp;" + sfxxlist[i].sflx
                                + "</li> ";
                        }
                        $("#nsrsfLi ul").append(sfxxconoption);
                        $("#nsrsfLi").css("display", "block");

                    } else {
                        alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                        return;
                    }
                }

            } else {
                alert("暂无法建立国税登记信息与地税登记信息关联关系。请重新选择，或到实体大厅办理“国地税对照关系管理”业务");
                return;
            }

        }

    }
    if (statusCode == '04') {
        // var gsdata=$('#selectValue2').val();
        var gsdata = $('#selectValue2').val();
        var gsjson = $.parseJSON(gsdata);
        // var dsdata=$('#selectValue3').val();
        var dsjson = $.parseJSON(data);
        var nsrzt = "";
        var kdqcc = "";
        var yxbz = "";
        if ((dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
            && dsjson.nsrzt_dm != '03' && dsjson.nsrzt_dm != '04'
            && dsjson.nsrzt_dm != '06' && dsjson.nsrzt_dm != '09')
            || dsjson.yxbz != 'Y') {

            if (dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
                && dsjson.nsrzt_dm != '03' && dsjson.nsrzt_dm != '04'
                && dsjson.nsrzt_dm != '06' && dsjson.nsrzt_dm != '09') {
                dsjson = "纳税人状态为" + dsjson.nsrztmc + ",";
            }
            if (dsjson.yxbz != 'Y') {
                yxbz = "有效标志为 N ,";
            }

            alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc + yxbz
                + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

            return;

        }

        if (gsjson.ssdabh == dsjson.ssdabh) {

            if (dsjson.kqccsztdjbz != 'N') {
                userxxObjec["dsuuid"] = dsjson.uuid

                userxxObjec["dsnsrmc"] = dsjson.nsrmc;
                userxxObjec["dszgswj_dm"] = dsjson.zgswj_dm;
                userxxObjec["dsgdghlx_dm"] = dsjson.gdghlx_dm;
                userxxObjec["dsnsrsbh"] = dsjson.nsrsbh;
                userxxObjec["dsdjxh"] = dsjson.djxh;
                userxxObjec["dsdjzclx_dm"] = dsjson.djzclx_dm;
                userxxObjec["dszgswskfj_dm"] = dsjson.zgswskfj_dm;
                userxxObjec["dsssdabh"] = dsjson.ssdabh;
                userxxObjec["dsnsrzt_dm"] = dsjson.nsrzt_dm;
                userxxObjec["dskzztdjlx_dm"] = dsjson.kzztdjlx_dm;
                userxxObjec["dsshxydm"] = dsjson.shxydm;
                if (dsnsrjbList.length > 0) {// 判断是否多天登记信息
                    var swjgoptionContent = "";
                    for (var i = 0; i < dsnsrjbList.length; i++) {
                        var sfmc;
                        var jsonString = JSON.stringify(dsnsrjbList[i]);
                        if (dsnsrjbList[i].zyhbz == 'Y') {
                            sfmc = '主用户';
                        } else {
                            if (dsnsrjbList[i].jyhlx == '02') {
                                sfmc = '大企业业务';
                            } else if (dsnsrjbList[i].jyhlx == '03') {
                                sfmc = '个人所得税业务';
                            }
                        }

                        swjgoptionContent += "<li value='kj'bz='kj' data='"
                            + jsonString + "' id='kjdsdjxx'>"
                            + dsnsrjbList[i].yhmc + "&nbsp;&nbsp;"
                            + dsnsrjbList[i].mrsj + "&nbsp;&nbsp;" + sfmc
                            + "</li> ";
                    }
                    $("#kqdssdnsrswjgLi ul").append(swjgoptionContent);
                    $("#kqdssdnsrswjgLi").css("display", "block");

                }
            } else {
                if (gsjson.nsrztDm == dsjson.nsrzt_dm || gsjson.nsrztDm == '06'
                    || dsjson.nsrzt_dm == '06') {
                    if (dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
                        && dsjson.nsrzt_dm != '03'
                        && dsjson.nsrzt_dm != '04'
                        && dsjson.nsrzt_dm != '06'
                        && dsjson.nsrzt_dm != '09') {
                        alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                        return;
                    }
                    userxxObjec["dsuuid"] = dsjson.uuid
                    userxxObjec["dsnsrmc"] = dsjson.nsrmc;
                    userxxObjec["dszgswj_dm"] = dsjson.zgswj_dm;
                    userxxObjec["dsgdghlx_dm"] = dsjson.gdghlx_dm;
                    userxxObjec["dsnsrsbh"] = dsjson.nsrsbh;
                    userxxObjec["dsdjxh"] = dsjson.djxh;
                    userxxObjec["dsdjzclx_dm"] = dsjson.djzclx_dm;
                    userxxObjec["dszgswskfj_dm"] = dsjson.zgswskfj_dm;
                    userxxObjec["dsssdabh"] = dsjson.ssdabh;
                    userxxObjec["dsnsrzt_dm"] = dsjson.nsrzt_dm;
                    userxxObjec["dskzztdjlx_dm"] = dsjson.kzztdjlx_dm;
                    userxxObjec["dsshxydm"] = dsjson.shxydm;
                    var sfxxconoption = "";
                    var sfxxlist = gsjson.sfxxlist
                    // 加上地税身份

                    for (var i = 0; i < sfxxlist.length; i++) {
                        var jsonString = JSON.stringify(sfxxlist[i]);
                        sfxxconoption += "<li value='gs'  bz='gs' data='"
                            + jsonString + "' id='nsrsfxx'>" + sfxxlist[i].xm
                            + "&nbsp;&nbsp;" + sfxxlist[i].sjhm
                            + "&nbsp;&nbsp;" + sfxxlist[i].sflx
                            + "</li> ";
                    }
                    for (var i = 0; i < dsnsrjbList.length; i++) {
                        var jsonString = JSON.stringify(dsnsrjbList[i]);
                        if (dsnsrjbList[i].zyhbz == 'N') {

                            if (dsnsrjbList[i].jyhlx == '02') {
                                sfmc = '大企业业务';
                            } else if (dsnsrjbList[i].jyhlx == '03') {
                                sfmc = '个人所得税业务';
                            }

                            sfxxconoption += "<li value='ds' bz='ds' data='"
                                + jsonString + "' id='nsrsfxx'>"
                                + dsnsrjbList[i].yhmc + "&nbsp;&nbsp;"
                                + dsnsrjbList[i].mrsj + "&nbsp;&nbsp;"
                                + sfmc + "</li> ";
                        }
                    }

                    $("#nsrsfLi ul").append(sfxxconoption);
                    $("#nsrsfLi").css("display", "block");

                } else {
                    alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                    return;
                }

            }
        } else {
            alert("暂无法建立国税登记信息与地税登记信息关联关系。请重新选择，或到实体大厅办理“国地税对照关系管理”业务");
            return;
        }

    }

}

var kj;

function kqdssfxxselectSwjg(value, data) {
    $("#nsrsfLi textarea").empty();
    $("#nsrsfLi ul").empty();
    $("#nsrsfLi textarea").append("<option>请选择纳税人身份信息</option>");
    $("#selectValue5").val("");

    // var data=$('#kqdssfswjgSelect option:selected').attr('data');

    if (null != data && data != "" && typeof data != "undefined") {
        var json = $.parseJSON(data);
        userxxObjec["dsyhmc"] = json.yhmc;
        userxxObjec["dsmrsj"] = json.mrsj;
        userxxObjec["dsjyhlx"] = json.jyhlx;// 旧用户类型
        userxxObjec["dsnsrsbh"] = json.nsrsbh;
        userxxObjec["dsshxydm"] = json.shxydm;
        userxxObjec["dszsyhbz"] = json.zsyhbz;
        userxxObjec["dsyhlx"] = json.yhlx; // 用户来源
        userxxObjec["dsyhId"] = json.yhId;
        //userxxObjec["statusCode"] = json;
        userxxObjec["dsyhuuid"] = json.uuid;
        userxxObjec["dsid"] = json.id;
        userxxObjec["gsid"] = "";
        // userxxObjec["gsid"]=json.id;
        $('#id').val(json.id);
        kj = value;
    }

    $("#captchCodeText").css("display", "block");
    $("#captchCodeText1").css("display", "none");

    $("#captchCodeText").prop("disabled", false);
    $("#captchCodeText").show();

    window.clearInterval(dsq);

    $("#captchCodeLi").css("display", "block");
    $("#nsrloginBtnLi").css("display", "block");

}

// 选择税务机关
function selectSwjg(value, data) {

    // var data=$('#swjgSelect option:selected').attr('data');
    var json = $.parseJSON(data);
    $("#nsrsfLi ul").empty();
    $("#nsrsfLi textarea").append("<option>请选择纳税人身份信息</option>");
    $("#nsrsfLi").css("display", "none");

    $("#kqdssdnsrswjgLi ul").empty();
    $("#kqdssdnsrswjgLi textarea").empty();
    $("#kqdssdnsrswjgLi textarea").append("<option>请选择身份信息</option>");
    $("#kqdssdnsrswjgLi").css("display", "none");

    if (statusCode == '05') {

        var sfxxlist = json.sfxxlist;

        var nsrzt = "";
        var kdqcc = "";
        var yxbz = "";
        if (json.kqccsztdjbz != 'N'
            || (json.nsrztDm != '01' && json.nsrztDm != '02'
                && json.nsrztDm != '03' && json.nsrztDm != '04'
                && json.nsrztDm != '06' && json.nsrztDm != '09')
            || json.yxbz != 'Y') {
            if (json.kqccsztdjbz != 'N') {
                kdqcc = "跨区财产主体登记为 Y,";
            }
            if (json.nsrztDm != '01' && json.nsrztDm != '02'
                && json.nsrztDm != '03' && json.nsrztDm != '04'
                && json.nsrztDm != '06' && json.nsrztDm != '09') {
                nsrzt = "纳税人状态为" + json.nsrztmc + ",";
            }
            if (json.yxbz != 'Y') {
                yxbz = "有效标志为 N ,";
            }

            alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc + yxbz
                + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

            return;

        }

        userxxObjec["gsuuid"] = json.uuid
        userxxObjec["gsdjxh"] = json.djsxh;
        userxxObjec["gsnsrsbh"] = json.nsrsbh;
        userxxObjec["gslrrDm "] = json.lrrDm;
        userxxObjec["gsdjxh"] = json.djxh;
        userxxObjec["gsssdabh"] = json.ssdabh;
        userxxObjec["gsnsrmc"] = json.nsrmc;
        userxxObjec["gskzztdjlxDm"] = json.kzztdjlxDm;
        userxxObjec["gsdjzclxDm"] = json.djzclxDm;
        userxxObjec["gszgswskfjDm"] = json.zgswskfjDm;
        userxxObjec["gsnsrztDm"] = json.nsrztDm;
        userxxObjec["gsgdghlxDm"] = json.gdghlxDm;
        userxxObjec["gsshxydm"] = json.shxydm;
        userxxObjec["gszgswjgDm"] = json.zgswjgDm;
        var dsdata = $('#dsswjgSelect option:selected').attr('data');
        var dsjson = $.parseJSON(dsdata);
        if (null != dsjson && dsjson != "" && typeof dsjson != "undefined") {

            //
            if (json.ssdabh == dsjson.ssdabh) {

                if (dsjson.kqccsztdjbz != 'N') {
                    userxxObjec["dsuuid"] = dsjson.uuid

                    userxxObjec["dsnsrmc"] = dsjson.nsrmc;
                    userxxObjec["dszgswj_dm"] = dsjson.zgswj_dm;
                    userxxObjec["dsgdghlx_dm"] = dsjson.gdghlx_dm;
                    userxxObjec["dsnsrsbh"] = dsjson.nsrsbh;
                    userxxObjec["dsdjxh"] = dsjson.djxh;
                    userxxObjec["dsdjzclx_dm"] = dsjson.djzclx_dm;
                    userxxObjec["dszgswskfj_dm"] = dsjson.zgswskfj_dm;
                    userxxObjec["dsssdabh"] = dsjson.ssdabh;
                    userxxObjec["dsnsrzt_dm"] = dsjson.nsrzt_dm;
                    userxxObjec["dskzztdjlx_dm"] = dsjson.kzztdjlx_dm;
                    userxxObjec["dsshxydm"] = dsjson.shxydm;
                    if (dsnsrjbList.length > 0) {// 判断是否多天登记信息
                        var swjgoptionContent = "";
                        for (var i = 0; i < dsnsrjbList.length; i++) {
                            var sfmc;
                            var jsonString = JSON.stringify(dsnsrjbList[i]);
                            if (dsnsrjbList[i].zyhbz == 'Y') {
                                sfmc = '主用户';
                            } else {
                                if (dsnsrjbList[i].jyhlx == '02') {
                                    sfmc = '大企业业务';
                                } else if (dsnsrjbList[i].jyhlx == '03') {
                                    sfmc = '个人所得税业务';
                                }
                            }

                            swjgoptionContent += "<li value='kj' bz='kj' data='"
                                + jsonString
                                + "' id='kjdsdjxx'>"
                                + dsnsrjbList[i].yhmc
                                + "&nbsp;&nbsp;"
                                + dsnsrjbList[i].mrsj
                                + "&nbsp;&nbsp;"
                                + sfmc + "</li> ";
                        }
                        $("#kqdssdnsrswjgLi ul").append(swjgoptionContent);
                        $("#kqdssdnsrswjgLi").css("display", "block");
                    }
                } else {

                    if (json.nsrztDm == dsjson.nsrzt_dm || json.nsrztDm == '06'
                        || dsjson.nsrzt_dm == '06') {

                        if (dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
                            && dsjson.nsrzt_dm != '03'
                            && dsjson.nsrzt_dm != '04'
                            && dsjson.nsrzt_dm != '06'
                            && dsjson.nsrzt_dm != '09') {
                            alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                            return;
                        }
                        var sfxxconoption = "";
                        var sfxxlist = json.sfxxlist
                        for (var i = 0; i < sfxxlist.length; i++) {
                            var jsonString = JSON.stringify(sfxxlist[i]);
                            sfxxconoption += "<li value='" + sfxxlist[i].sjhm
                                + "' data='" + jsonString
                                + "' id='nsrsfxx'>" + sfxxlist[i].xm
                                + "&nbsp;&nbsp;" + sfxxlist[i].sjhm
                                + "&nbsp;&nbsp;" + sfxxlist[i].sflx
                                + "</li> ";
                        }
                        $("#nsrsfLi ul").append(sfxxconoption);
                        $("#nsrsfLi").css("display", "block");

                    } else {
                        alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                        return;
                    }

                }

            } else {
                alert("暂无法建立国税登记信息与地税登记信息关联关系。请重新选择，或到实体大厅办理“国地税对照关系管理”业务");
                return;
            }

        }
    }
    if (statusCode == '04') {
        var nsrzt = "";
        var kdqcc = "";
        var yxbz = "";
        if (json.kqccsztdjbz != 'N'
            || (json.nsrztDm != '01' && json.nsrztDm != '02'
                && json.nsrztDm != '03' && json.nsrztDm != '04'
                && json.nsrztDm != '06' && json.nsrztDm != '09')
            || json.yxbz != 'Y') {
            if (json.kqccsztdjbz != 'N') {
                kdqcc = "跨区财产主体登记为 Y,";
            }
            if (json.nsrztDm != '01' && json.nsrztDm != '02'
                && json.nsrztDm != '03' && json.nsrztDm != '04'
                && json.nsrztDm != '06' && json.nsrztDm != '09') {
                nsrzt = "纳税人状态为" + json.nsrztmc + ",";
            }
            if (json.yxbz != 'Y') {
                yxbz = "有效标志为 N ,";
            }

            alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc + yxbz
                + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

            return;

        }
        userxxObjec["gsuuid"] = json.uuid
        userxxObjec["gsdjxh"] = json.djsxh;
        userxxObjec["gsnsrsbh"] = json.nsrsbh;
        userxxObjec["gslrrDm "] = json.lrrDm;
        userxxObjec["gsdjxh"] = json.djxh;
        userxxObjec["gsssdabh"] = json.ssdabh;
        userxxObjec["gsnsrmc"] = json.nsrmc;
        userxxObjec["gskzztdjlxDm"] = json.kzztdjlxDm;
        userxxObjec["gsdjzclxDm"] = json.djzclxDm;
        userxxObjec["gszgswskfjDm"] = json.zgswskfjDm;
        userxxObjec["gszgswjgDm"] = json.zgswjgDm;
        userxxObjec["gsnsrztDm"] = json.nsrztDm;
        userxxObjec["gsgdghlxDm"] = json.gdghlxDm;
        userxxObjec["gsshxydm"] = json.shxydm;
        userxxObjec["gszgswjgDm"] = json.zgswjgDm;
        var dsdata = $('#dsswjgSelect option:selected').attr('data');
        var dsjson = $.parseJSON(dsdata);
        if (null != dsjson && dsjson != "" && typeof dsjson != "undefined") {
            //
            if (json.ssdabh == dsjson.ssdabh) {
                if (dsjson.kqccsztdjbz != 'N') {
                    userxxObjec["dsuuid"] = dsjson.uuid

                    userxxObjec["dsnsrmc"] = dsjson.nsrmc;
                    userxxObjec["dszgswj_dm"] = dsjson.zgswj_dm;
                    userxxObjec["dsgdghlx_dm"] = dsjson.gdghlx_dm;
                    userxxObjec["dsnsrsbh"] = dsjson.nsrsbh;
                    userxxObjec["dsdjxh"] = dsjson.djxh;
                    userxxObjec["dsdjzclx_dm"] = dsjson.djzclx_dm;
                    userxxObjec["dszgswskfj_dm"] = dsjson.zgswskfj_dm;
                    userxxObjec["dsssdabh"] = dsjson.ssdabh;
                    userxxObjec["dsnsrzt_dm"] = dsjson.nsrzt_dm;
                    userxxObjec["dskzztdjlx_dm"] = dsjson.kzztdjlx_dm;
                    userxxObjec["dsshxydm"] = dsjson.shxydm;
                    if (dsnsrjbList.length > 0) {// 判断是否多天登记信息
                        var swjgoptionContent = "";
                        for (var i = 0; i < dsnsrjbList.length; i++) {
                            var sfmc;
                            var jsonString = JSON.stringify(dsnsrjbList[i]);
                            if (dsnsrjbList[i].zyhbz == 'Y') {
                                sfmc = '主用户';
                            } else {
                                if (dsnsrjbList[i].jyhlx == '02') {
                                    sfmc = '大企业业务';
                                } else if (dsnsrjbList[i].jyhlx == '03') {
                                    sfmc = '个人所得税业务';
                                }
                            }

                            swjgoptionContent += "<li value='kj'bz='kj'  data="
                                + jsonString + ">" + dsnsrjbList[i].yhmc
                                + "&nbsp;&nbsp;" + dsnsrjbList[i].mrsj
                                + "&nbsp;&nbsp;" + sfmc + "</li> ";
                        }
                        $("#kqdssdnsrswjgLi ul").append(swjgoptionContent);
                        $("#kqdssdnsrswjgLi").css("display", "block");
                    }
                } else {

                    if (json.nsrztDm == dsjson.nsrzt_dm || json.nsrztDm == '06'
                        || dsjson.nsrzt_dm == '06') {
                        if (dsjson.nsrzt_dm != '01' && dsjson.nsrzt_dm != '02'
                            && dsjson.nsrzt_dm != '03'
                            && dsjson.nsrzt_dm != '04'
                            && dsjson.nsrzt_dm != '06'
                            && dsjson.nsrzt_dm != '09') {
                            alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                            return;
                        }
                        var sfxxconoption = "";
                        var sfxxlist = json.sfxxlist
                        for (var i = 0; i < sfxxlist.length; i++) {
                            var jsonString = JSON.stringify(sfxxlist[i]);
                            sfxxconoption += "<option value='"
                                + sfxxlist[i].sjhm + "' data=" + jsonString
                                + ">" + sfxxlist[i].xm + "&nbsp;&nbsp;"
                                + sfxxlist[i].sjhm + "&nbsp;&nbsp;"
                                + sfxxlist[i].sflx + "</option> ";
                        }

                        for (var i = 0; i < dsnsrjbList.length; i++) {
                            var jsonString = JSON.stringify(dsnsrjbList[i]);
                            if (dsnsrjbList[i].zyhbz == 'N') {
                                sfxxconoption += "<li value='ds' data='"
                                    + jsonString + "' id='nsrsfxx'>"
                                    + dsnsrjbList[i].yhmc + "&nbsp;&nbsp;"
                                    + dsnsrjbList[i].mrsj + "</li> ";
                            }
                        }

                        $("#nsrsfLi ul").append(sfxxconoption);
                        $("#nsrsfLi").css("display", "block");

                    } else {
                        alert("尊敬的纳税人您好，因您选择的纳税人状态不一致，无法登陆网上税务局，请重新选择或到大厅进行纳税人状态变更");
                        return;
                    }
                }

            } else {
                alert("暂无法建立国税登记信息与地税登记信息关联关系。请重新选择，或到实体大厅办理“国地税对照关系管理”业务");
                return;
            }

        }
    }

    if (statusCode == '06') {
        var nsrzt = "";
        var kdqcc = "";
        var yxbz = "";
        if (json.kqccsztdjbz != 'N'
            || (json.nsrztDm != '01' && json.nsrztDm != '02'
                && json.nsrztDm != '03' && json.nsrztDm != '04'
                && json.nsrztDm != '06' && json.nsrztDm != '09')
            || json.yxbz != 'Y') {
            if (json.kqccsztdjbz != 'N') {
                kdqcc = "跨区财产主体登记为 Y,";
            }
            if (json.nsrztDm != '01' && json.nsrztDm != '02'
                && json.nsrztDm != '03' && json.nsrztDm != '04'
                && json.nsrztDm != '06' && json.nsrztDm != '09') {
                nsrzt = "纳税人状态为" + json.nsrztmc + ",";
            }
            if (json.yxbz != 'Y') {
                yxbz = "有效标志为 N ,";
            }

            alert("敬的纳税人您好，因您选择的" + nsrzt + kdqcc + yxbz
                + "无法登陆网上税务局，请到实体大厅处理登记相关信息");

            return;

        }

        userxxObjec["gsuuid"] = json.uuid
        userxxObjec["gszgswjgDm"] = json.zgswjgDm;
        userxxObjec["gsdjxh"] = json.djsxh;
        userxxObjec["gsnsrsbh"] = json.nsrsbh;
        userxxObjec["gslrrDm "] = json.lrrDm;
        userxxObjec["gsdjxh"] = json.djxh;
        userxxObjec["gsssdabh"] = json.ssdabh;
        userxxObjec["gsnsrmc"] = json.nsrmc;
        userxxObjec["gskzztdjlxDm"] = json.kzztdjlxDm;
        userxxObjec["gsdjzclxDm"] = json.djzclxDm;
        userxxObjec["gszgswskfjDm"] = json.zgswskfjDm;
        userxxObjec["gsnsrztDm"] = json.nsrztDm;
        userxxObjec["gsgdghlxDm"] = json.gdghlxDm;
        userxxObjec["gsshxydm"] = json.shxydm;
        var sfxxlist = json.sfxxlist;

        if (sfxxlist.length > 0) {

            var sfxxoptionContent = "";
            for (var i = 0; i < sfxxlist.length; i++) {
                var jsonString = JSON.stringify(sfxxlist[i]);
                sfxxoptionContent += "<li value='" + sfxxlist[i].sjhm
                    + "' data='" + jsonString + "' id='nsrsfxx'>"
                    + sfxxlist[i].xm + "&nbsp;&nbsp;" + sfxxlist[i].sjhm
                    + "&nbsp;&nbsp;" + sfxxlist[i].sflx + "</li> ";
            }

            $("#nsrsfLi ul").append(sfxxoptionContent);

            $("#nsrsfLi").css("display", "block");

        } else {
            alert("无身份信息!请核实数据");
            return;
        }

    }

}

var smdjxh;
var smsflxdm;
var smid;

function getsmrzjk(djxh, sflxdm, id) {

    var smrz = "N";
    var bz = 'Y';
    if (smdjxh != djxh) {
        smdjxh = djxh;
        bz = 'N';
    }
    if (smsflxdm != sflxdm) {
        smsflxdm = sflxdm;
        bz = 'N';
    }
    if (smid != id) {
        smid = id;
        bz = 'N';
    }

    if (bz == 'N') {
        $.ajax({
            url: "/sso/loginsc/querySmrz.do",
            type: "post",
            async: false,
            data: {
                "djxh": djxh,
                "sflxdm": sflxdm,
                "id": id
            },
            headers: {
                "Accept": "text/plain;charset=UTF-8"
            },
            dataType: 'json',
            success: function (data, status) {

                if (data.sfcgbz) {
                    smrz = 'N';
                } else {
                    if (data.rtmcode != '0000') {

                        smrz = 'N';
                    } else {
                        smrz = 'Y';
                    }
                }
            },
            error: function (data, status, e) {
                alert("ca验证失败");
                smrz = 'N';
            }
        });
    } else {
        smrz = sfsmrz;
    }
    return smrz;
}

var bz;
var sfsmrz;

// 选择纳税人
function selectNsrxx(value, data) {
    $("#captchCodeLi").css("display", "block");
    $("#nsrloginBtnLi").css("display", "block");
    // var data=$('#nasrSelect option:selected').attr('data');
    // var bz=$('#nasrSelect option:selected').val();

    var json = $.parseJSON(data);
    var sjhm;
    var id;
    if (statusCode == '04') {
        sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);
        if (value == "gs" && sfsmrz == 'Y') {
            userxxObjec["gssfzlxDm"] = json.sfzlxDm;
            userxxObjec["gssfzjhm"] = json.sfzjhm;
            userxxObjec["gsxm"] = json.xm;
            userxxObjec["gssjhm"] = json.sjhm;
            userxxObjec["gssflx"] = json.sflx;
            userxxObjec["gsid"] = json.id;
            bz = "gs";
            id = json.id;
            sjhm = json.sjhm;
        } else if (value == "ds") {
            userxxObjec["gssfzlxDm"] = "";
            userxxObjec["gssfzjhm"] = "";
            userxxObjec["gsxm"] = json.yhmc;
            userxxObjec["gssjhm"] = "";
            userxxObjec["gssflx"] = "";
            userxxObjec["dsyhmc"] = json.yhmc;
            userxxObjec["dsmrsj"] = json.mrsj;
            userxxObjec["dsjyhlx"] = json.jyhlx;// 旧用户类型
            userxxObjec["dsnsrsbh"] = json.nsrsbh;
            userxxObjec["dsshxydm"] = json.shxydm;
            userxxObjec["dszsyhbz"] = json.zsyhbz;
            userxxObjec["dsyhlx"] = json.yhlx; // 用户来源
            userxxObjec["dsyhId"] = json.yhId;
            userxxObjec["dsyhuuid"] = json.uuid;
            userxxObjec["gsid"] = "";
            id = json.id;
            bz = "ds";
            sjhm = json.mrsj;
            alert("尊敬的纳税人，您选择的身份登录网上税务局后只能办理部分业务");
        } else {

            // var ewm=getEwm(userxxObjec);
            /*
             * if(null != ewm && ewm != "" && typeof ewm != "undefined"){ var
             * img="尊敬的纳税人，为保障您的合法权益，该身份登录网上税务局需要办理实名认证。请下载“天府e税”完成实名认证 或者
             * 请下载“12366掌厅”完成实名认证<br/>" +"<img
             * src='data:image/png;base64,"+ewm+"'>" +"<img
             * src='data:image/png;base64,"+ewm+"'>"; alert(img);
             *
             * }else{
             */
            var djxh = userxxObjec.gsdjxh;
            var nsrsbh = userxxObjec.gsnsrsbh;
            var sflx = "01";
            smrzxxewm(djxh, nsrsbh, sflx);
            /* } */

            // return;
        }

    }
    if (statusCode == '05') {
        sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);
        if (sfsmrz == 'Y') {
            userxxObjec["gssfzlxDm"] = json.sfzlxDm;
            userxxObjec["gssfzjhm"] = json.sfzjhm;
            userxxObjec["gsxm"] = json.xm;
            userxxObjec["gssjhm"] = json.sjhm;
            userxxObjec["gssflx"] = json.sflx;
            userxxObjec["gsid"] = json.id;
            sjhm = json.sjhm;
            id = json.id;
        } else {
            var djxh = userxxObjec.gsdjxh;
            var nsrsbh = userxxObjec.gsnsrsbh;
            var sflx = "01";

            smrzxxewm(djxh, nsrsbh, sflx);
            // alert(iframe1);
            // return;
        }
    }
    if (statusCode == '06') {
        sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);
        if (sfsmrz == 'Y') {
            userxxObjec["gssfzlxDm"] = json.sfzlxDm;
            userxxObjec["gssfzjhm"] = json.sfzjhm;
            userxxObjec["gsxm"] = json.xm;
            userxxObjec["gssjhm"] = json.sjhm;
            userxxObjec["gssflx"] = json.sflx;
            userxxObjec["gsid"] = json.id;
            sjhm = json.sjhm;
            id = json.id;
        } else {
            var djxh = userxxObjec.gsdjxh;
            var nsrsbh = userxxObjec.gsnsrsbh;
            var sflx = "01";

            smrzxxewm(djxh, nsrsbh, sflx);
            // return;
        }
    }

    // $("#captchCodeText").html("发送验证码");
    $("#captchCodeText").css("display", "block");
    $("#captchCodeText1").css("display", "none");

    $("#captchCodeText").prop("disabled", false);
    $("#captchCodeText").show();

    window.clearInterval(dsq);

}

var dsq;

function getEwm(userxxObjec) {

    var djxh = userxxObjec.gsdjxh;
    var nsrsbh = userxxObjec.gsnsrsbh;
    var sflx = "01";

    $.post("/sso/loginsc/queryEwm.do?djxh="
        + djxh + "&nsrsbh=" + nsrsbh + "&sflx=" + sflx, {}, function (d) {

        if (d.sfcgbz) {
            return "";
        } else {
            return d.ewm;
        }
    });

}

function sndnsryzm() {
    var id = $("#id").val();
    var sfcgbz = 'N';
    var returnMessage;

    window.connt = 60;
    $("#captchCodeText").html(connt + "s后重新获取验证码");
    $("#captchCodeText").prop("disabled", true);
    var codeInterval = setInterval(function () {
        if (window.connt == 0) {
            clearInterval(codeInterval);
            $("#captchCodeText").html("重新获取验证码");
            $("#captchCodeText").show();
            $("#captchCodeText").prop("disabled", false);

        } else {
            $("#captchCodeText").html((connt - 1) + "s后重新获取验证码");
            $("#captchCodeText").prop("disabled", true);
            window.connt--;
        }
    }, 1000);
    dsq = codeInterval;

    $.ajax({
        url: "/sso/loginsms/sendSms.do",
        type: "post",
        async: false,
        data: {
            "id": id

        },
        headers: {
            "Accept": "text/plain;charset=UTF-8"
        },
        dataType: 'json',
        success: function (data, status) {


            if (data.returnCode != '0000') {
                clearInterval(codeInterval);
                $("#captchCodeText").html("重新获取验证码");
                $("#captchCodeText").show();
                $("#captchCodeText").prop("disabled", false);
                alert(data.returnMessage);
            }

        },
        error: function (data, status, e) {
            alert("验证码发送失败");
        }
    });

    if (sfcgbz == 'N') {
        alert(returnMessage);
        return;
    }


}

function getYzm() {

    if (document.getElementById("captchCodeText").disabled) {
        return;
    }

    var id = $("#id").val();
    $("#captchCodeLi").css("display", "block");
    $("#nsrloginBtnLi").css("display", "block");
    var data = $('#selectValue5').val();
    // var bz=$('#nasrSelect option:selected').val();
    // var kj =$('#kqdssfswjgSelect option:selected').val();

    var json = $.parseJSON(data);
    var sjhm;
    var id;
    if (statusCode == '01') {

        if (userxxObjec.dsmrsj == "手机号码异常") {
            alert("手机号码异常");
            return;
        }

    }
    if (statusCode == '04') {
        if (kj == 'kj') {
            var kjdata = $('#selectValue4').val();
            var kjjson = $.parseJSON(kjdata);
            userxxObjec["gssfzlxDm"] = "";
            userxxObjec["gssfzjhm"] = "";
            userxxObjec["gsxm"] = kjjson.yhmc;
            userxxObjec["gssjhm"] = "";
            userxxObjec["gssflx"] = "";
            userxxObjec["gsid"] = "";
            userxxObjec["dsyhmc"] = kjjson.yhmc;
            userxxObjec["dsmrsj"] = kjjson.mrsj;
            userxxObjec["dsjyhlx"] = kjjson.jyhlx;// 旧用户类型
            userxxObjec["dsnsrsbh"] = kjjson.nsrsbh;
            userxxObjec["dsshxydm"] = kjjson.shxydm;
            userxxObjec["dszsyhbz"] = kjjson.zsyhbz;
            userxxObjec["dsyhlx"] = kjjson.yhlx; // 用户来源
            userxxObjec["dsyhId"] = kjjson.yhId;
            userxxObjec["dsyhuuid"] = kjjson.uuid;
            id = kjjson.id;
            sjhm = kjjson.mrsj;
            if (sjhm == "手机号码异常") {
                alert("手机号码异常");
                return;
            }
        } else {
            sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);

            if (bz == "gs" && sfsmrz == 'Y') {
                userxxObjec["gssfzlxDm"] = json.sfzlxDm;
                userxxObjec["gssfzjhm"] = json.sfzjhm;
                userxxObjec["gsxm"] = json.xm;
                userxxObjec["gssjhm"] = json.sjhm;
                userxxObjec["gssflx"] = json.sflx;
                id = json.id;
                sjhm = json.sjhm;
                if (sjhm == "手机号码异常") {
                    alert("手机号码异常");
                    return;
                }
            } else if (bz == "ds") {
                userxxObjec["gssfzlxDm"] = "";
                userxxObjec["gssfzjhm"] = "";
                userxxObjec["gsxm"] = json.yhmc;
                userxxObjec["gssjhm"] = "";
                userxxObjec["gssflx"] = "";
                userxxObjec["dsyhmc"] = json.yhmc;
                userxxObjec["dsmrsj"] = json.mrsj;
                userxxObjec["dsjyhlx"] = json.jyhlx;// 旧用户类型
                userxxObjec["dsnsrsbh"] = json.nsrsbh;
                userxxObjec["dsshxydm"] = json.shxydm;
                userxxObjec["dszsyhbz"] = json.zsyhbz;
                userxxObjec["dsyhlx"] = json.yhlx; // 用户来源
                userxxObjec["dsyhId"] = json.yhId;
                userxxObjec["dsyhuuid"] = json.uuid;
                id = json.id;
                sjhm = json.mrsj;
                if (sjhm == "手机号码异常") {
                    alert("手机号码异常");
                    return;
                }

            } else {
                var djxh = userxxObjec.gsdjxh;
                var nsrsbh = userxxObjec.gsnsrsbh;
                var sflx = "01";
                smrzxxewm(djxh, nsrsbh, sflx);
                // alert(iframe1);
                return;
            }

        }
    }
    if (statusCode == '05') {
        if (kj == 'kj') {
            var kjdata = $('#selectValue4').val();
            var kjjson = $.parseJSON(kjdata);
            userxxObjec["gssfzlxDm"] = "";
            userxxObjec["gssfzjhm"] = "";
            userxxObjec["gsxm"] = kjjson.yhmc;
            userxxObjec["gssjhm"] = "";
            userxxObjec["gssflx"] = "";
            userxxObjec["gsid"] = "";
            userxxObjec["dsyhmc"] = kjjson.yhmc;
            userxxObjec["dsmrsj"] = kjjson.mrsj;
            userxxObjec["dsjyhlx"] = kjjson.jyhlx;// 旧用户类型
            userxxObjec["dsnsrsbh"] = kjjson.nsrsbh;
            userxxObjec["dsshxydm"] = kjjson.shxydm;
            userxxObjec["dszsyhbz"] = kjjson.zsyhbz;
            userxxObjec["dsyhlx"] = kjjson.yhlx; // 用户来源
            userxxObjec["dsyhId"] = kjjson.yhId;
            userxxObjec["dsyhuuid"] = kjjson.uuid;
            id = kjjson.id;
            sjhm = kjjson.mrsj;
            if (sjhm == "手机号码异常") {
                alert("手机号码异常");
                return;
            }

        } else {
            sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);
            if (sfsmrz == 'Y') {
                userxxObjec["gssfzlxDm"] = json.sfzlxDm;
                userxxObjec["gssfzjhm"] = json.sfzjhm;
                userxxObjec["gsxm"] = json.xm;
                userxxObjec["gssjhm"] = json.sjhm;
                userxxObjec["gssflx"] = json.sflx;
                sjhm = json.sjhm;
                id = json.id;
                if (sjhm == "手机号码异常") {
                    alert("手机号码异常");
                    return;
                }

            } else {
                var djxh = userxxObjec.gsdjxh;
                var nsrsbh = userxxObjec.gsnsrsbh;
                var sflx = "01";
                smrzxxewm(djxh, nsrsbh, sflx);
                return;
            }
        }
    }
    if (statusCode == '06') {
        sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);
        if (sfsmrz == 'Y') {
            userxxObjec["gssfzlxDm"] = json.sfzlxDm;
            userxxObjec["gssfzjhm"] = json.sfzjhm;
            userxxObjec["gsxm"] = json.xm;
            userxxObjec["gssjhm"] = json.sjhm;
            userxxObjec["gssflx"] = json.sflx;
            sjhm = json.sjhm;
            id = json.id;
            if (sjhm == "手机号码异常") {
                alert("手机号码异常");
                return;
            }
        } else {
            var djxh = userxxObjec.gsdjxh;
            var nsrsbh = userxxObjec.gsnsrsbh;
            var sflx = "01";
            smrzxxewm(djxh, nsrsbh, sflx);
            return;
        }
    }

    if (null == id || id == "" || typeof id == "undefined") {
        alert("请选择身份后再发送短信");
        return;
    }

    //$("#id").val(id);
    $("#sjhm").val(sjhm);
    /*$.post("/sso/loginsms/sendSms.do?id=" + id,
            {}, function(smsxx) {
            }).error(function() {
    });
*/


    window.connt = 60;
    //$("#dsnsrswjgLi").css("display","block");
    $("#captchCodeText").css("display", "none");
    $("#captchCodeText1").html(window.connt + "s后重新获取验证码");
    $("#captchCodeText1").css({
        "display": "block",
        "cursor": "default",
        "color": "#888888"
    });

    $("#captchCodeText").attr("disabled", true);
    var codeInterval = setInterval(function () {
        if (connt == 0) {
            clearInterval(codeInterval);
            $("#captchCodeText").html("重新获取验证码");
            $("#captchCodeText1").css("display", "none");
            $("#captchCodeText").css("display", "block");
            $("#captchCodeText").show();
            $("#captchCodeText").attr("disabled", false);
        } else {
            $("#captchCodeText1").html((connt - 1) + "s后重新获取验证码");
            $("#captchCodeText1").css("display", "block");
            $("#captchCodeText").css("display", "none");
            $("#captchCodeText").attr("disabled", true);
            connt--;
        }
    }, 1000);
    dsq = codeInterval;
    var sfcgbz = 'N';
    var returnMessage;
    $.ajax({
        url: "/sso/loginsms/sendSms.do",
        type: "post",
        async: false,
        data: {
            "id": id

        },
        headers: {
            "Accept": "text/plain;charset=UTF-8"
        },
        dataType: 'json',
        success: function (data, status) {

            if (data.sfcgbz) {
                sfcgbz = 'N';
                returnMessage = '验证码发送失败';

            } else {
                if (data.returnCode != '0000') {
                    clearInterval(codeInterval);
                    $("#captchCodeText").html("重新获取验证码");
                    $("#captchCodeText1").css("display", "none");
                    $("#captchCodeText").css("display", "block");
                    $("#captchCodeText").show();
                    $("#captchCodeText").attr("disabled", false);
                    alert(data.returnMessage);
                }
            }
        },
        error: function (data, status, e) {

            alert("验证码发送失败");
        }
    });


}

function upLogin() {
    var id = $('#id').val();
    $("#captchCodeLi").css("display", "block");
    $("#nsrloginBtnLi").css("display", "block");
    var data = $('#selectValue5').val();
    //var bz=$('#nasrSelect option:selected').val();
    //var kj =$('#kqdssfswjgSelect option:selected').val();

    var json = $.parseJSON(data);
    var sjhm;
    var id;
    if (statusCode == '01') {
        if (userxxObjec.dsmrsj == "手机号码异常") {
            alert("手机号码异常");
            return;
        }
    }
    if (statusCode == '04') {
        if (kj == 'kj') {
            var kjdata = $('#selectValue4').val();
            var kjjson = $.parseJSON(kjdata);
            userxxObjec["gssfzlxDm"] = "";
            userxxObjec["gssfzjhm"] = "";
            userxxObjec["gsxm"] = kjjson.yhmc;
            userxxObjec["gssjhm"] = "";
            userxxObjec["gssflx"] = "";
            userxxObjec["gsid"] = "";
            userxxObjec["dsyhmc"] = kjjson.yhmc;
            userxxObjec["dsmrsj"] = kjjson.mrsj;
            userxxObjec["dsjyhlx"] = kjjson.jyhlx;//旧用户类型
            userxxObjec["dsnsrsbh"] = kjjson.nsrsbh;
            userxxObjec["dsshxydm"] = kjjson.shxydm;
            userxxObjec["dszsyhbz"] = kjjson.zsyhbz;
            userxxObjec["dsyhlx"] = kjjson.yhlx; //用户来源
            userxxObjec["dsyhId"] = kjjson.yhId;
            userxxObjec["dsyhuuid"] = kjjson.uuid;
            id = kjjson.id;
            sjhm = kjjson.mrsj;
            if (sjhm == "手机号码异常") {
                alert("手机号码异常");
                return;
            }
        } else {

            sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);
            if (bz == "gs" && sfsmrz == 'Y') {
                userxxObjec["gssfzlxDm"] = json.sfzlxDm;
                userxxObjec["gssfzjhm"] = json.sfzjhm;
                userxxObjec["gsxm"] = json.xm;
                userxxObjec["gssjhm"] = json.sjhm;
                userxxObjec["gssflx"] = json.sflx;
                id = json.id;
                sjhm = json.sjhm;
                if (sjhm == "手机号码异常") {
                    alert("手机号码异常");
                    return;
                }
            } else if (bz == "ds") {
                userxxObjec["gssfzlxDm"] = "";
                userxxObjec["gssfzjhm"] = "";
                userxxObjec["gsxm"] = json.yhmc;
                userxxObjec["gssjhm"] = "";
                userxxObjec["gssflx"] = "";
                userxxObjec["dsyhmc"] = json.yhmc;
                userxxObjec["dsmrsj"] = json.mrsj;
                userxxObjec["dsjyhlx"] = json.jyhlx;//旧用户类型
                userxxObjec["dsnsrsbh"] = json.nsrsbh;
                userxxObjec["dsshxydm"] = json.shxydm;
                userxxObjec["dszsyhbz"] = json.zsyhbz;
                userxxObjec["dsyhlx"] = json.yhlx; //用户来源
                userxxObjec["dsyhId"] = json.yhId;
                userxxObjec["dsyhuuid"] = json.uuid;
                id = json.id;
                sjhm = json.mrsj;
                if (sjhm == "手机号码异常") {
                    alert("手机号码异常");
                    return;
                }

            } else {
                var djxh = userxxObjec.gsdjxh;
                var nsrsbh = userxxObjec.gsnsrsbh;
                var sflx = "01";
                smrzxxewm(djxh, nsrsbh, sflx);

                //alert(iframe1);
                return;
            }


        }
    }
    if (statusCode == '05') {
        if (kj == 'kj') {
            var kjdata = $('#selectValue4').val();
            var kjjson = $.parseJSON(kjdata);
            userxxObjec["gssfzlxDm"] = "";
            userxxObjec["gssfzjhm"] = "";
            userxxObjec["gsxm"] = kjjson.yhmc;
            userxxObjec["gssjhm"] = "";
            userxxObjec["gssflx"] = "";
            userxxObjec["gsid"] = "";
            userxxObjec["dsyhmc"] = kjjson.yhmc;
            userxxObjec["dsmrsj"] = kjjson.mrsj;
            userxxObjec["dsjyhlx"] = kjjson.jyhlx;//旧用户类型
            userxxObjec["dsnsrsbh"] = kjjson.nsrsbh;
            userxxObjec["dsshxydm"] = kjjson.shxydm;
            userxxObjec["dszsyhbz"] = kjjson.zsyhbz;
            userxxObjec["dsyhlx"] = kjjson.yhlx; //用户来源
            userxxObjec["dsyhId"] = kjjson.yhId;
            userxxObjec["dsyhuuid"] = kjjson.uuid;
            id = kjjson.id;
            sjhm = kjjson.mrsj;
            if (sjhm == "手机号码异常") {
                alert("手机号码异常");
                return;
            }


        } else {
            sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);

            if (sfsmrz == 'Y') {
                userxxObjec["gssfzlxDm"] = json.sfzlxDm;
                userxxObjec["gssfzjhm"] = json.sfzjhm;
                userxxObjec["gsxm"] = json.xm;
                userxxObjec["gssjhm"] = json.sjhm;
                userxxObjec["gssflx"] = json.sflx;
                sjhm = json.sjhm;
                id = json.id;
                if (sjhm == "手机号码异常") {
                    alert("手机号码异常");
                    return;
                }


            } else {
                var djxh = userxxObjec.gsdjxh;
                var nsrsbh = userxxObjec.gsnsrsbh;
                var sflx = "01";
                smrzxxewm(djxh, nsrsbh, sflx);
                return;
            }
        }
    }
    if (statusCode == '06') {
        sfsmrz = getsmrzjk(userxxObjec.gsdjxh, json.sflxDm, json.id);
        if (sfsmrz == 'Y') {
            userxxObjec["gssfzlxDm"] = json.sfzlxDm;
            userxxObjec["gssfzjhm"] = json.sfzjhm;
            userxxObjec["gsxm"] = json.xm;
            userxxObjec["gssjhm"] = json.sjhm;
            userxxObjec["gssflx"] = json.sflx;
            sjhm = json.sjhm;
            id = json.id;
            if (sjhm == "手机号码异常") {
                alert("手机号码异常");
                return;
            }
        } else {
            var djxh = userxxObjec.gsdjxh;
            var nsrsbh = userxxObjec.gsnsrsbh;
            var sflx = "01";

            smrzxxewm(djxh, nsrsbh, sflx);

            return;
        }
    }

    if (null == id || id == "" || typeof id == "undefined") {
        alert("请选择身份后再发送短信");
        return;
    }
    $("#id").val(id);
    $("#sjhm").val(sjhm);


    var captchCode = $("#captchCode").val();

    var index = layer.load(0, {shade: 0.2});
    var dsdghbz = userxxObjec.dsdghbz;
    var gsdghbz = userxxObjec.gsdghbz;
    var isshxydm = userxxObjec.isshxydm;
    var dsckjywr = userxxObjec.dsckjywr;

    //window.confirm(111);

    var index = layer.load(0, {shade: 0.2});
    if (gsdghbz == 'Y') {
        if (!window.confirm("尊敬的纳税人，暂未在地税找到您的登记信息，在8月1日前您暂时能够登陆网上税务局并办理部分业务，请尽快到主管税务机关大厅完成地税登记相关事宜")) {
            layer.close(index);
            return;
        }
    }
    if (dsdghbz == 'Y' && dsckjywr != 'Y' && isshxydm == 'Y') {
        if (!window.confirm("尊敬的纳税人，暂未在国税找到您的登记信息，在8月1日前您暂时能够登陆网上税务局并办理部分业务，请尽快到主管税务机关大厅完成国税登记的相关事宜")) {
            layer.close(index);
            return;
        }
    }


    var nsrsbh = $('#nsrsbh').val();
    var id = $("#id").val();
    var index = layer.load(0, {shade: 0.2});
    $.post("/sso/loginsms/verificationSms.do?sjhm=" + sjhm + "&yzm=" + captchCode + "&id=" + id, {}, function (yzxx) {
        var sfcg = yzxx.sfcg;
        if (sfcg) {
            var jsonMap = {};

            if (null != userxxObjec.gsnsrsbh && userxxObjec.gsnsrsbh != "" && typeof userxxObjec.gsnsrsbh != "undefined") {
                userxxObjec['nsrsbh'] = userxxObjec.gsnsrsbh;
            } else {
                userxxObjec['nsrsbh'] = userxxObjec.dsnsrsbh;
            }

            if (null != userxxObjec.gsshxydm && userxxObjec.gsshxydm != "" && typeof userxxObjec.gsshxydm != "undefined") {
                userxxObjec['shxydm'] = userxxObjec.gsshxydm;
            } else {
                userxxObjec['shxydm'] = userxxObjec.dsshxydm;
            }

            userxxObjec['yzmcode'] = 'Y';


            $('#userName').val(JSON.stringify(userxxObjec));
            $("#upLoginForm").submit();
        } else {
            layer.close(index);
            alert("验证失败");
        }
    }).error(function () {
        alert("登录失败");
        layer.close(index);
        return;
    });

}


function smrzxxewm(djxh, nsrsbh, sflx) {
    /*	var iframe1="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td  style=\"border-style:none\" colspan=\"3\">尊敬的纳税人，为保障您的合法权益，该身份登录网上税务局需要办理实名认证。请下载“天府e税”完成实名认证 或者 请下载“12366掌厅”完成实名认证</td></tr>"
              +" <tr> <td align=\"center\" style=\"border-style:none\">“天府e税”</td><td style=\"width:20%;border-style:none\"></td><td align=\"center\" style=\"border-style:none\">“12366掌厅”</td></tr>"
        +"<tr><td style=\"width:40%;height:40%;border-style:none\"><img style=\"width:100%;height:100%;\" src='/sso/style/images/lhewm.png'></td><td style=\"width:20%;border-style:none\"></td> "
              +"<td style=\"width:40%;height:40%;border-style:none\"><img style=\"width:100%;height:100%;\" src='/sso/loginsc/queryEwm.do?djxh="+djxh+"&nsrsbh="+nsrsbh+"&sflx="+sflx+"'></td></tr>"
            +" </table>";*/
    var iframe1 = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td  style=\"border-style:none\" >尊敬的纳税人，为保障您的合法权益，该身份登录网上税务局需要办理实名认证。请下载“12366掌厅”完成实名认证 </td></tr>"
        + "<tr ><td style=\"width:100%;height:100%;border-style:none\" align=\"center\"><img style=\"width:100%;height:100%;\" src='/sso/loginsc/queryEwm.do?djxh=" + djxh + "&nsrsbh=" + nsrsbh + "&sflx=" + sflx + "'></td></tr>"
        + " <tr> <td align=\"center\" style=\"border-style:none\">“12366掌厅”</td></tr></table>";


    alert(iframe1);
}

function loginDbrwxx() {


    var djxh = userxxObjec.gsdjxh;
    var nsrsbh = userxxObjec.gsnsrsbh;
    var zgswskfjDm = userxxObjec.gszgswskfjDm;
    var nsrmc = userxxObjec.gsnsrmc;
    $.post("/sso/loginsc/queryzjDb.do",
        {
            "djxh": djxh,
            "nsrsbh": nsrsbh,
            "zgswskfjDm": zgswskfjDm,
            "nsrmc": nsrmc
        }, function (d) {

        });
}

