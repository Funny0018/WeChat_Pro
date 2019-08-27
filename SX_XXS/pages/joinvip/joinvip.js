// pages/joinvip/joinvip.js
var app = getApp();
var types = {};
Page({

  /**
   * 页面的初始数据
   */
  data: {
    vipTypes: {},
    typeid: 0,
    totalprice: 0,
    fullname: '',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    isVip: '普通用户',
    joinvip: '立即开通会员'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

    var that = this;

    var userinfo = '';
    app.getUserInfoForApp().then(function(res) {
      if (res.status == 200) {
        userinfo = JSON.parse(res.data.massage);
        app.globalData.userAppInfo = userinfo
        wx.setStorageSync("userAppInfo", userinfo)
        var isvip = userinfo.fisvip + app.globalData.bayVip;
        var vipdate = '';
        var joinVip = '立即开通';
        if (isvip > 0) {
          isvip = '尊享黑金会员';
          joinVip = '立即续费会员卡'
        } else {
          isvip = '普通用户';
          joinVip = '立即开通会员'
        }
        // 
        that.setData({
          isVip: isvip,
          joinvip: joinVip
        })
      }
    })

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var that = this;
    wx.request({
      url: app.globalData.appurl + '/wxGetVipType',
      success: function(res) {
        types = JSON.parse(res.data.massage);
        that.setData({
          vipTypes: types,
          typeid: types[0].fid,
          totalprice: types[0].fprice,
          fullname: types[0].ffullname
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  changeType: function(res) {
    var typeid = res.currentTarget.id;
    var index = 0;
    for (var item in types) {
      if (types[item].fid == typeid) {
        index = item;
      }
    }
    this.setData({
      typeid: types[index].fid,
      totalprice: types[index].fprice,
      fullname: types[index].ffullname
    })
  },
  buyVip: function() {
    if (this.data.typeid == 8 && app.globalData.tehui == 1) {

      wx.showModal({
        title: 'VIP办理',
        content: '您在本月已购买过特价会员',
        showCancel: false,
        success: function(res) {

        }
      })
    } else {

      var info = '{"userid":' + app.globalData.userAppInfo.fuserid + ',"amount":"' + this.data.totalprice + '","viptype":"' + this.data.typeid + '"}';
      wx.request({
        url: app.globalData.appurl + '/wxPayVip',
        data: {
          info: info
        },
        success: function(res) {
          var obj = JSON.parse(res.data.massage);
          wx.requestPayment({
            timeStamp: obj.timeStamp,
            nonceStr: obj.nonceStr,
            package: obj.package,
            signType: 'MD5',
            paySign: obj.paySign,
            success: function(res) {
              app.globalData.bayVip = 1;
              wx.showModal({
                title: 'VIP办理',
                content: '恭喜您办理成功，因为微信支付延迟，请稍后三至无分钟即可生效',
                showCancel: false,
                success: function(res) {

                  wx.setStorageSync('buyVip', '1')
                  var isproduct = wx.getStorageSync('product-vip');
                  wx.removeStorageSync('product-vip');
                  if (isproduct == 1) {
                    wx.navigateBack({

                    })
                  } else if (isproduct == 2) {
                    wx.navigateBack({

                    })
                  } else {
                    wx.switchTab({
                      url: '/pages/me/me'
                    })
                  }
                }
              })
            },
            fail: function(res) {},
            complete: function(res) {}
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    if (e.detail.errMsg != 'getUserInfo:fail auth deny') {
      app.globalData.userInfo = e.detail.userInfo
      this.setData({
        userInfo: e.detail.userInfo,
        hasUserInfo: true
      })
    }
  },
})