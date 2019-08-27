//app.js
const Promise = require('/static/script/bluebird.js');
App({
  onLaunch: function() {
    this.globalData.userAppInfo = '';
    // this.getUserInfoForApp();
    var that = this;
    wx.getSystemInfo({
      success: function(res) {
        wx.setStorage({
          key: 'systemInfo',
          data: res,
        });
        that.globalData.ww = res.windowWidth;
        that.globalData.wh = res.windowHeight;
        that.globalData.phoneModel = res.model;
      },
    })
    var times = wx.getStorageSync("LoginTimes");
    // wx.setStorageSync("LoginTimes", 1);

    if (times == '') {
      wx.setStorageSync("LoginTimes", 1);
    } else {
      wx.setStorageSync("LoginTimes", times + 1);
    }
  },
  getUserInfoForApp: function() {
    var that = this;
    //
    return new Promise(function(resolve, reject) {
      // 调用登录接口
      wx.login({
        success: function(res) {

          if (that.globalData.userAppInfo == '') {
            wx.request({
              url: that.globalData.appurl + '/wxLogin',
              data: {
                "code": res.code,
                "userInfo": that.globalData.userInfo
              },
              success: function(res) {
                that.globalData.userAppInfo = JSON.parse(res.data.massage);
                wx.setStorageSync("userAppInfo", JSON.parse(res.data.massage));
                that.globalData.userID = JSON.parse(res.data.massage).fuserid;
                wx.setStorageSync('userID', JSON.parse(res.data.massage).fuserid);
                that.globalData.tehui = res.data.tehui;
                that.globalData.jiesheng = res.data.jiesheng;
                that.globalData.days = res.data.days;

                that.globalData.pageSize = res.data.pagesize;
                that.globalData.orderpageSize = res.data.orderpagesize;
                that.globalData.yunfei = res.data.yunfei;
                that.globalData.baoyou = res.data.baoyou;
                that.globalData.optionCity = res.data.massage.split(',');
                that.globalData.openshare = res.data.openshare;
                that.globalData.recharge = res.data.reCharge; 
                var res = {
                  status: 200
                }
                resolve(res);

              }
            })
          } else {
            that.updateAppInfo();
            var res = {
              status: 200
            }
            resolve(res);
          }
        }
      });
      wx.getSetting({
        success: res => {
          if (res.authSetting['scope.userInfo']) {
            // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
            wx.getUserInfo({
              success: res => {
                // 可以将 res 发送给后台解码出 unionId
                if (that.globalData.userAppInfo != '') {}
                that.globalData.userInfo = res.userInfo
                // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                // 所以此处加入 callback 以防止这种情况
                if (that.userInfoReadyCallback) {
                  that.userInfoReadyCallback(res)
                }
              }
            })
          }
        }
      })
    });
  },
  getBaseConfigForMain: function() {
      var that = this;
      //
      return new Promise(function(resolve, reject) {
        wx.request({
          url: that.globalData.appurl + '/wxGetBaseConfig',
          success: function(res) {
            that.globalData.pageSize = res.data.pagesize;
            that.globalData.orderpageSize = res.data.orderpagesize;
            that.globalData.yunfei = res.data.yunfei;
            that.globalData.baoyou = res.data.baoyou;
            that.globalData.optionCity = res.data.massage.split(',');
            that.globalData.openshare = res.data.openshare;
            that.globalData.recharge = res.data.reCharge; 
            // if (res.massage.indexOf() > -1) {
            //   that.globalData.optionCity = res.data.massage.split(',');
            // } else {
            //   that.globalData.optionCity = res.massage
            // }
            var res = {
              status: 200
            }
            resolve(res);
          }
        })
      })
    }

    ,
  getBaseConfig: function() {
    var that = this;
    wx.request({
      url: that.globalData.appurl + '/wxGetBaseConfig',
      success: function(res) {
        that.globalData.pageSize = res.data.pagesize;
        that.globalData.orderpageSize = res.data.orderpagesize;
        that.globalData.yunfei = res.data.yunfei;
        that.globalData.baoyou = res.data.baoyou;
        that.globalData.optionCity = res.data.massage.split(',');
        that.globalData.openshare = res.data.openshare;
        that.globalData.recharge = res.data.reCharge; 
        // if (res.massage.indexOf() > -1) {
        //   that.globalData.optionCity = res.data.massage.split(',');
        // } else {
        //   that.globalData.optionCity = res.massage
        // }
      }
    })
  },
  updateAppInfo: function() {
    var that = this;
    var useinfo = this.globalData.userAppInfo;
    if (this.globalData.userID == '') {
      this.globalData.userID = wx.getStorageSync("userID");
    }
    return new Promise(function(resolve, reject) {
      wx.request({
        url: that.globalData.appurl + '/wxUpdateUserInfo',
        data: {
          "fuserid": that.globalData.userID,
          "userInfo": that.globalData.userInfo
          // "fuserid": 21
        },
        success: function(res) {
          if (res.data.massage != '') {

            that.globalData.userAppInfo = JSON.parse(res.data.massage);
            // that.globalData.userAppId = res.data.massage;
            that.globalData.tehui = res.data.tehui;
            that.globalData.jiesheng = res.data.jiesheng;
            that.globalData.days = res.data.days;
            that.globalData.userID = JSON.parse(res.data.massage).fuserid
            wx.setStorageSync("userAppInfo", JSON.parse(res.data.massage));
            wx.setStorageSync('userID', JSON.parse(res.data.massage).fuserid);

            that.globalData.pageSize = res.data.pagesize;
            that.globalData.orderpageSize = res.data.orderpagesize;
            that.globalData.yunfei = res.data.yunfei;
            that.globalData.baoyou = res.data.baoyou;
            that.globalData.optionCity = res.data.optionalcity.split(',');
            that.globalData.openshare = res.data.openshare;
            that.globalData.recharge = res.data.reCharge; 
            resolve(res);

          }
        }
      })
    })
  },
  bezier: function(pots, amount) {
    var pot;
    var lines;
    var ret = [];
    var points;
    for (var i = 0; i <= amount; i++) {
      points = pots.slice(0);

      lines = [];
      while (pot = points.shift()) {
        if (points.length) {
          lines.push(pointLine([pot, points[0]], i / amount));
        } else if (lines.length > 1) {
          points = lines;
          lines = []
        } else {
          break;
        }
      }
      ret.push(lines[0]);
    }

    function pointLine(points, rate) {
      var pointA, pointB, pointDistance, xDistance, yDistance, tan, radian, tmpPointDistance;
      var ret = [];
      pointA = points[0];
      pointB = points[1];
      xDistance = pointB.X - pointA.X;
      yDistance = pointB.Y - pointA.Y;
      pointDistance = Math.pow(Math.pow(xDistance, 2) + Math.pow(yDistance, 2), 1 / 2);
      tan = yDistance / xDistance;
      radian = Math.atan(tan);
      tmpPointDistance = pointDistance * rate;
      ret = {
        X: pointA.X + tmpPointDistance * Math.cos(radian),
        Y: pointA.Y + tmpPointDistance * Math.sin(radian)
      };
      return ret;
    }
    return {
      'bezier_points': ret
    }
  },
  cartForDetail: function(obj) {
    var objCart = obj;
    var totalPrice = 0;
    var totalVipPrice = 0;
    var finalPrice = 0;
    for (var item in objCart) {
      if (objCart[item].fVipPrice > 0) {
        totalVipPrice += objCart[item].fVipPrice * objCart[item].count;
      } else {
        totalVipPrice += objCart[item].fPrice * objCart[item].count;
      }
      totalPrice += objCart[item].fPrice * objCart[item].count;
    }
    if (this.globalData.userAppInfo.fisvip > 0) {
      finalPrice = totalVipPrice;
    } else {
      finalPrice = totalPrice;
    }
    if (finalPrice < this.globalData.baoyou && finalPrice > 0) {
      return (finalPrice + this.globalData.yunfei).toFixed(2) + ',' + (this.globalData.baoyou - finalPrice).toFixed(2);
    } else if (finalPrice == 0) {
      return finalPrice.toFixed(2) + ',' + (this.globalData.baoyou - finalPrice).toFixed(2);
    } else {
      return finalPrice.toFixed(2) + ',' + 0;
    }

  },
  globalData: {
    userID: '',
    userInfo: null,
    userAppInfo: '',
    address: '',
    province: '定位错误',
    qqMapKey: '7GJBZ-P5Q3X-J744P-T35UR-MNXA5-WZFUM',
    deliveryAddrssCity: '',
    appurl: 'https://xxs.xixiansheng.cn/WX_JVXXS',
    // appurl: 'http://localhost:8080',
    pageSize: 5,
    orderpageSize: 5,
    ww: 0,
    wh: 0,
    yunfei: 3,
    baoyou: 0,
    optionCity: '',
    tehui: 0,
    jiesheng: 0.00,
    days: 0,
    phoneModel: '',
    openshare: 0,
    bayVip: 0, recharge:0

  }
})