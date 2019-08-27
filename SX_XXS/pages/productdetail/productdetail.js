// pages/productdetail/productdetail.js
var app = getApp();
var objCart = '';
var obj = '';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fproductid: '',
    imgs: new Array(),
    fname: '',
    fdesc: '',
    fPrice: '',
    fVipPrice: '',
    count: 0,
    tags: '',
    fsalled: 0,
    fdetailImg: '',
    move: 1,
    totalPrice: '',
    infoPrice: '',
    dialogShow: 'none',
    ftoptag: '',
    autoplay: false,
    // videos: [{
    //   fvideourl: 'https://xxs.xixiansheng.cn/Images/video/productDetail/1.mp4'
    // }],
    videos: '',
    fistejia:0,
    fleftcount:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

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
    var fproductid = wx.getStorageSync('productDetail');
    this.objCart = wx.getStorageSync('cartProduct');
    var totalcount = 0;
    for (var item in this.objCart) {
      totalcount += this.objCart[item].count;
    }
    this.setData({
      count: totalcount
    });
    var that = this;
    var retPriceInfo = app.cartForDetail(this.objCart).split(',');
    var priceInfo = '';
    if (retPriceInfo[1] > 0) {
      priceInfo = '还差' + retPriceInfo[1] + '元免配送费';
    } else {
      priceInfo = '已免配送费';
    }
    var dialogShow = 'none';
    if (retPriceInfo[0] > 0) {
      dialogShow = 'display';
    }
    this.setData({
      totalPrice: "￥" + retPriceInfo[0],
      infoPrice: priceInfo,
      dialogShow: dialogShow
    })
    wx.request({
      url: app.globalData.appurl + '/wxGetProductDetail',
      data: {
        fproductid: fproductid
      },
      success: function(res) {
        var info = JSON.parse(res.data.massage);
        var toptap = info.ftoptag;
        if (toptap == 'null') {
          toptap = '';
        } else if (toptap == '') {
          toptap = '';
        } else {
          toptap = toptap.split(',');
        }

        that.obj = info;
        that.setData({
          fproductid: info.fProductId,
          imgs: info.fproductImgs,
          videos: info.fproductVideo,
          fname: info.fName,
          fdesc: info.fDesc,
          fPrice: info.fPrice,
          fVipPrice: info.fVipPrice,
          tags: info.tags,
          fsalled: info.fsalled,
          fdetailImg: info.fdetailImg,
          ftoptag: toptap,
          fistejia: info.fistejia,
          fleftcount: info.fleftcount
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {
    wx.setStorageSync('cartProduct', this.objCart)
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {
    wx.setStorageSync('cartProduct', this.objCart)

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
  toCart: function() {
    wx.switchTab({
      url: '/pages/cart/cart',
    })

  },
  toJoinVip: function(res) {
    // wx.switchTab({
    //   url: '/pages/vip/vip',
    //   success: function(res) {},
    //   fail: function(res) {},
    //   complete: function(res) {},
    // })
    wx.setStorageSync('product-vip', '1')
    wx.navigateTo({
      url: '/pages/joinvip/joinvip',
    })
  },
  intoCart: function() {
    this.setData({
      move: 0
    })
    var productId = this.data.fproductid;
    var has = 0;
    for (var item in this.objCart) {
      if (this.objCart[item].fProductId == productId) {
        has = 1;
      }
    }
    if (has == 0) {
      this.obj.count = 1;
      if (this.objCart == '' || typeof this.objCart != 'object') {
        this.objCart = new Array();
        this.objCart.push(this.obj);
      } else {
        this.objCart.push(this.obj);
      }
    } else {
      for (var item in this.objCart) {
        if (this.objCart[item].fProductId == productId) {
          this.objCart[item].count = this.objCart[item].count * 1 + 1;
        }
      }
    }
    this.setData({
      count: this.data.count + 1
    })
    var retPriceInfo = app.cartForDetail(this.objCart).split(',');
    var priceInfo = '';
    if (retPriceInfo[1] > 0) {
      priceInfo = '还差' + retPriceInfo[1] + '元免配送费';
    } else {
      priceInfo = '已免配送费';
    }
    var dialogShow = 'none';
    if (retPriceInfo[0] > 0) {
      dialogShow = 'display';
    }
    this.setData({
      totalPrice: "￥" + retPriceInfo[0],
      infoPrice: priceInfo,
      dialogShow: dialogShow
    })
    var that = this;
    setTimeout(function() {
      that.setData({
        move: 1
      })
    }, 300)
  }
})