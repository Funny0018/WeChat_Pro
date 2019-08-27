// pages/me/me.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

    // userInfo: {
    //   nickName: '点击登录',
    //   avatarUrl: '/static/image/me/touxiang.png'
    // },
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    vipMoney: 0,
    coupons: '0张',
    isVip: '普通用户',
    vipdate: '',
    joinVip: '立即开通',
    loading: true,
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
    this.setData({
      loading: false
    })
    var that = this;
    var buyVip = wx.getStorageSync('buyVip');
    wx.setStorage({
      key: 'buyVip',
      data: '0',
    })
    if (buyVip == '1') {
      wx.showModal({
        title: '购买提示',
        content: '购买会员成功',
        showCancel: false
      })
    }
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
          vipdate = '您的会员还剩' + app.globalData.days + '天到期';
          joinVip = '立即续费';
        } else {
          isvip = '普通用户';
          joinVip = '立即开通';
          vipdate = '';
        }
        // 
        that.getUserCoupon();
        that.setData({
          vipMoney: app.globalData.userAppInfo.fmoney,
          isVip: isvip,
          vipdate: vipdate,
          joinVip: joinVip,
          loading: true
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
    //       vipdate = '您的会员还剩' + app.globalData.days + '天到期';
    //       joinVip = '立即续费';
    //     } else {
    //       isvip = '普通用户';
    //       joinVip = '立即开通';
    //       vipdate = '';
    //     }
    //     // 
    //     that.setData({
    //       vipMoney: app.globalData.userAppInfo.fmoney,
    //       isVip: isvip,
    //       vipdate: vipdate,
    //       joinVip: joinVip,
    //       loading: true
    //     })
    //   })
    // } else {
    //   app.getUserInfoForApp().then(function(res) {
    //     userinfo = app.globalData.userAppInfo;
    //     wx.setStorageSync("userAppInfo", userinfo)
    //     var isvip = userinfo.fisvip;
    //     var vipdate = '';
    //     var joinVip = '立即开通';
    //     if (isvip == '1') {
    //       isvip = '尊享黑金会员';
    //       vipdate = '您的会员还剩' + app.globalData.days + '天到期';
    //       joinVip = '立即续费';
    //     } else {
    //       isvip = '普通用户';
    //       joinVip = '立即开通';
    //       vipdate = '';
    //     }
    //     // 
    //     that.setData({
    //       vipMoney: app.globalData.userAppInfo.fmoney,
    //       isVip: isvip,
    //       vipdate: vipdate,
    //       joinVip: joinVip,
    //       loading: true
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
  todelivery: function() {
    wx.navigateTo({
      url: '/pages/deliveryAddress/deliveryAddress'
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
  toOrder: function(res) {
    var data = res.currentTarget.id;
    wx.setStorageSync('orderType', data)
    wx.navigateTo({
      url: '/pages/order/order',
    })
  },
  joinVip: function() {
    wx.navigateTo({
      url: '/pages/joinvip/joinvip',
    })
  },
  toRecharge: function() {
    var isrecharge = app.globalData.recharge;
    console.log(isrecharge)
    if (isrecharge > 0) {
      wx.navigateTo({
        url: '/pages/recharge/recharge',
      })
    }
  },
  tapCall: function() {
    wx.makePhoneCall({
      phoneNumber: '13127350088',
    })
  },
  test: function() {
    // wx.navigateTo({
    //   url: '/pages/award/award',
    // })
    wx.navigateTo({
      url: '/pages/share/share',
    })
  },
  toShare: function() {
    wx.navigateTo({
      url: '/pages/share/share',
    })
  },
  getUserCoupon: function() {
    var that = this;
    wx.request({
      url: app.globalData.appurl + '/wxGetUserCoupon',
      data: {
        "fuserid": app.globalData.userAppInfo.fuserid
      },
      success: function(res) {
        var obj = JSON.parse(res.data.massage);
        var hasCoupon = 0;
        var couponCount = 0;
        for (var i in obj) {
          if (obj[i].canUse == '1') {
            if (obj[i].ftype == "金额抵扣") {
              couponCount = couponCount + 1;
            } else if (obj[i].ftype == "商品抵扣") {
              for (var j in objCart) {
                couponCount = couponCount + 1;
              }
            }
          }
        }
        // if (couponCount > 0) {
        that.setData({
          coupons: couponCount
        })


      }
    })
  },
  toCoupon: function() {
    wx.navigateTo({
      url: '/pages/userCoupon/userCoupon',
    })
  }
})