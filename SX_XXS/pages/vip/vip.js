// pages/vip/vip.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    isVip: '普通用户',
    joinvip: '立即开通会员',
    loading: true,
    jiesheng: 0.00
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

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var that = this;
    this.setData({

      loading: false,
    })
    var userinfo = '';


    app.getUserInfoForApp().then(function(res) {
      if (res.status == 200) {
        userinfo = app.globalData.userAppInfo;
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
          joinvip: joinVip,
          jiesheng: (app.globalData.jiesheng * 1).toFixed(2),
          loading: true,
        })
      }
    })

    // if (app.globalData.userAppInfo != null && app.globalData.userAppInfo != '') {
    //   app.updateAppInfo().then(function(res) {
    //     userinfo = app.globalData.userAppInfo;
    //     wx.setStorageSync("userAppInfo", userinfo)
    //     var isvip = userinfo.fisvip;
    //     var vipdate = '';
    //     var joinVip = '立即开通';
    //     if (isvip == '1') {
    //       isvip = '尊享黑金会员';
    //       joinVip = '立即续费会员卡'
    //     } else {
    //       isvip = '普通用户';
    //       joinVip = '立即开通会员'
    //     }
    //     // 
    //     that.setData({
    //       isVip: isvip,
    //       joinvip: joinVip,
    //       jiesheng: (app.globalData.jiesheng * 1).toFixed(2),
    //       loading: true,
    //     })
    //   })
    // } else {
    //   app.getUserInfoForApp().then(function(res) {
    //     var userinfo = app.globalData.userAppInfo;
    //     wx.setStorageSync("userAppInfo", userinfo)
    //     var isvip = userinfo.fisvip;
    //     var vipdate = '';
    //     var joinVip = '立即开通';
    //     if (isvip == '1') {
    //       isvip = '尊享黑金会员';
    //       joinVip = '立即续费会员卡'
    //     } else {
    //       isvip = '普通用户';
    //       joinVip = '立即开通会员'
    //     }
    //     // 
    //     that.setData({
    //       isVip: isvip,
    //       joinvip: joinVip,
    //       jiesheng: (app.globalData.jiesheng*1).toFixed(2),
    //       loading: true,
    //     })
    //   })
    // }



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
  joinVip: function() {
    wx.navigateTo({
      url: '/pages/joinvip/joinvip',
    })
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