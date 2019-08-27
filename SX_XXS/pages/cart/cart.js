// pages/cart/cart.js
var app = getApp();
var product = "";
var objCart = "";
var objCoupon = "";
var checkProductId = new Array();
var totalcount = 0;
var appUserInfo = '';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    viptext: '会员可节省',
    product: "",
    totalpricevip: '￥0.00',
    totalprice: 0.00,
    selectedAllStatus: true,
    dikouid: 0,
    dikoutitle: '抵扣券',
    dikouprice: '无可用抵扣券',
    dikoucolor: '#ddd',
    hongbaotitle: '红包',
    hongbaoprice: '无可用红包',
    hongbaocolor: '#ddd',
    finalProductPrice: '￥0',
    yunfei: '￥0',
    finalPrice: '￥0',
    fvipamount: '￥0',
    payprice: '￥0',
    userInfo: {},
    hasUserInfo: false,
    loginView: false,
    baoyou: 0,
    tobaoyou: "",
    isvip: 0,
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
    var userinfo = '';

    app.getUserInfoForApp().then(function(res) {
      if (res.status == 200) {
        userinfo = app.globalData.userAppInfo;
        that.appUserInfo = app.globalData.userAppInfo;
        wx.setStorageSync("userAppInfo", userinfo)
        var isvip = userinfo.fisvip+app.globalData.bayVip;
        objCart = wx.getStorageSync('cartProduct');
        if (objCart == '') {
          wx.clearStorageSync('couponInfo')
        }
        var totalprice = 0;
        for (var itemcart in objCart) {
          if (objCart[itemcart].count < 0) {
            objCart[itemcart].count = 0;
          }
          totalcount += objCart[itemcart].count;
          break;
        }
        // /////////////////////
        // this.setData({
        //   product: obj
        // });

        checkProductId = new Array();
        for (var item in objCart) {
          // checkProductId += objCart[item].fProductId + ',';

          checkProductId.push(objCart[item].fProductId + '');
          totalprice = totalprice * 1 + objCart[item].fPrice * objCart[item].count;
          objCart[item].checked = true;
        }
        that.setData({
          totalprice: totalprice,
          product: objCart,
          selectedAllStatus: true,
          yunfei: '￥' + app.globalData.yunfei,
          isvip: isvip
        });
        that.setCartPriceTabBar();
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

    var newObjCart = new Array();
    for (var item in objCart) {
      if (objCart[item].count > 0) {
        newObjCart.push(objCart[item]);
      }
    }
    wx.setStorageSync('cartProduct', newObjCart)
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

  chooseProduct: function(e) {
    checkProductId = e.detail.value;
    for (var item in objCart) {
      // if (checkProductId.includes(objCart[item].fProductId + '')) {

      if (checkProductId.indexOf(objCart[item].fProductId + '') >= 0) {
        objCart[item].checked = true;
      } else {
        objCart[item].checked = false;
      }
    }
    this.setCartPriceTabBar();
  },
  viewcheck: function(e) {
    checkProductId = e.detail.value;
    this.setCartPriceTabBar();
  },
  bindPlus: function(event) {
    var productId = event.target.id.split('-')[1];
    for (var t in objCart) {
      if (objCart[t].fProductId == productId) {
        objCart[t].count = objCart[t].count * 1 + 1;
      }
    }
    this.setCartPriceTabBar();
    this.setData({
      product: objCart
    });
  },
  bindMinus: function(event) {
    var productId = event.target.id.split('-')[1];
    var totalPrice = '0';
    var index = "";
    var cartindex = "";
    var that = this;
    for (var t in objCart) {
      if (objCart[t].fProductId == productId) {
        index = t;
      }
    }

    if ((objCart[index].count * 1 - 1) <= 0) {
      wx.showModal({
        title: '是否删除？',
        content: '',
        success: function(res) {
          if (res.confirm) {
            objCart[index].count = 0;
            that.setCartPriceTabBar();
            that.setData({
              product: objCart
            });
          }
        }
      })
    } else {
      objCart[index].count = objCart[index].count * 1 - 1;
      this.setCartPriceTabBar();
      this.setData({
        product: objCart
      });
    }

  },
  setCartPriceTabBar: function() {
    var totalcount = 0;
    var totalprice = 0;
    var totalnovip = 0;
    var totalvip = 0;
    var payprice = 0;
    var finalPrice = 0;
    var viptext = this.data.viptext;
    var maxvipamount = this.appUserInfo.fmoney;
    if (maxvipamount < 0) {
      maxvipamount = 0;
    }
    var vipamount = 0;



    var that = this;
    var dikouInfo = wx.getStorageSync("couponInfo");

    var couponCount = 0;
    if (objCoupon == "") {
      wx.request({
        url: app.globalData.appurl + '/wxGetUserCoupon',
        data: {
          "fuserid": app.globalData.userAppInfo.fuserid
        },
        success: function(res) {
          objCoupon = JSON.parse(res.data.massage);
          var obj = objCoupon;
          var hasCoupon = 0;

          if (dikouInfo.split('|').length > 1) {
            for (var i in obj) {
              if (obj[i].fid == dikouInfo.split('|')[0]) {
                that.setData({
                  dikouid: dikouInfo.split('|')[0],
                  dikoutitle: dikouInfo.split('|')[1],
                  dikouprice: '￥' + dikouInfo.split('|')[2] * 1,
                  dikoucolor: "#f60"
                })
              }
            }

          } else {
            var couponCount = 0;
            var obj = objCoupon;
            for (var i in obj) {
              if (obj[i].canUse == '1') {
                if (obj[i].ftype == "金额抵扣") {
                  if (obj[i].fminprice <= that.data.payprice) {
                    couponCount = couponCount + 1;
                  }
                } else if (obj[i].ftype == "商品抵扣") {
                  for (var j in objCart) {
                    if (objCart[j].fProductId == obj[i].fproductId) {
                      couponCount = couponCount + 1;
                    }
                  }
                }
              }
            }
            if (couponCount > 0) {
              that.setData({
                dikoutitle: '抵扣券',
                dikouprice: couponCount + "个抵扣券可用",
                dikoucolor: "#f60"
              })
            } else {
              that.setData({
                dikoutitle: '抵扣券',
                dikouprice: '无可用抵扣券',
                dikoucolor: '#ddd',
              })
            }
          }
        }
      })
    } else {
      // objCoupon = JSON.parse(res.data.massage);
      var obj = objCoupon;
      var hasCoupon = 0;

      if (dikouInfo.split('|').length > 1) {
        for (var i in obj) {
          if (obj[i].fid == dikouInfo.split('|')[0]) {
            that.setData({
              dikouid: dikouInfo.split('|')[0],
              dikoutitle: dikouInfo.split('|')[1],
              dikouprice: '￥' + dikouInfo.split('|')[2] * 1,
              dikoucolor: "#f60"
            })
          }
        }
      } else {
        var couponCount = 0;
        var obj = objCoupon;
        for (var i in obj) {
          if (obj[i].canUse == '1') {
            if (obj[i].ftype == "金额抵扣") {
              if (obj[i].fminprice <= that.data.payprice) {
                couponCount = couponCount + 1;
              }
            } else if (obj[i].ftype == "商品抵扣") {
              for (var j in objCart) {
                if (objCart[j].fProductId == obj[i].fproductId) {
                  couponCount = couponCount + 1;
                }
              }
            }
          }
        }
        if (couponCount > 0) {
          that.setData({
            dikoutitle: '抵扣券',
            dikouprice: couponCount + "个抵扣券可用",
            dikoucolor: "#f60"
          })
        } else {
          that.setData({
            dikoutitle: '抵扣券',
            dikouprice: '无可用抵扣券',
            dikoucolor: '#ddd',
          })
        }
      }
    }




    for (var item in objCart) {
      totalcount += objCart[item].count;
      // if (checkProductId.includes(objCart[item].fProductId + '')) {
      if (checkProductId.indexOf(objCart[item].fProductId + '') >= 0) {
        if (objCart[item].fVipPrice > 0) {
          totalvip += objCart[item].fVipPrice * objCart[item].count;
        } else {
          totalvip += objCart[item].fPrice * objCart[item].count;
        }
        totalnovip += objCart[item].fPrice * objCart[item].count;
        // if (this.appUserInfo.fisvip == 1 && Date.parse(this.appUserInfo.fvipenddate) > Date.parse(new Date())) {
        // if (this.data.isvip == 1 && Date.parse(this.appUserInfo.fvipenddate.replace(/-/g, "/")) > Date.parse((new Date()).toISOString())) {
        if (this.data.isvip >0  && app.globalData.days > 0) {
          viptext = '会员已节省';
          // viptext = (new Date()).toISOString();
          totalprice = totalvip;
        } else {
          totalprice = totalnovip;
        }
      }
    }
    if (this.toFloat(totalprice) >= app.globalData.baoyou) {
      this.setData({
        yunfei: '包邮',
        tobaoyou: ''
      })
    } else if (this.toFloat(totalprice) == 0) {
      this.setData({
        yunfei: '￥0',
        tobaoyou: ''
      })
    } else {
      this.setData({
        yunfei: '￥' + app.globalData.yunfei,
        tobaoyou: '实付满' + (app.globalData.baoyou * 1).toFixed(2) + '元包邮，还差' + (app.globalData.baoyou - this.toFloat(totalprice)).toFixed(2) + '元，去凑单'
      })
    }
    var finalProductPrice = 0;

    finalProductPrice = (totalprice * 1 -
      (this.data.dikoutitle == '抵扣券' ? 0 : this.toFloat(this.data.dikouprice)) -
      (this.data.hongbaotitle == '红包' ? 0 : this.toFloat(this.data.hongbaoprice)) + this.toFloat(this.data.yunfei) );
    if (this.toFloat(maxvipamount) > this.toFloat(finalProductPrice)) {
      vipamount = finalProductPrice;
    } else {
      vipamount = maxvipamount;
    }
    payprice = this.toFloat(finalProductPrice);
    finalPrice = payprice - vipamount;







    this.setData({
      totalprice: totalprice.toFixed(2),
      finalProductPrice: '￥' + finalProductPrice.toFixed(2),
      finalPrice: '￥' + finalPrice.toFixed(2),
      fvipamount: '￥' + vipamount.toFixed(2),
      totalpricevip: '￥' + (totalnovip - totalvip).toFixed(2),
      payprice: '￥' + payprice.toFixed(2),
      viptext: viptext
    });
    if (totalcount != 0) {
      wx.setTabBarBadge({
        index: 1,
        text: totalcount + '',
      })
    } else {
      wx.removeTabBarBadge({
        index: 1,
      })
    }

  },
  toBill: function() {
    var that = this;
    if (checkProductId != '') {
      if (this.data.hasUserInfo == false) {
        this.setData({
          loginView: true
        })
      } else {
        if (that.toFloat(this.data.fvipamount) > 0) {
          wx.showModal({
            title: '',
            content: '本单将抵扣' + this.data.fvipamount + "元余额",
            showCancel: false,
            confirmColor: '#f60',
            success: function() {
              that.toBillInfo();
            }
          })
        } else {
          that.toBillInfo();
        }
      }
    } else {
      wx.showModal({
        title: '',
        content: '请选择结算商品',
        showCancel: false,
        confirmColor: '#f60'
      })
    }

  },
  toBillInfo: function() {
    var that = this;
    var billProduct = new Array();
    for (var item in objCart) {
      // if (checkProductId.indexOf(',') >= 0) {
      if (checkProductId.length >= 0) {
        // for (var no in checkProductId.split(',')) {
        for (var no in checkProductId) {
          // if (objCart[item].fProductId == checkProductId.split(',')[no] && objCart[item].count > 0) {
          if (objCart[item].fProductId == checkProductId[no] && objCart[item].count > 0) {
            billProduct.push(objCart[item]);
            break;
          }
        }
      } else {
        if (objCart[item].fProductId == checkProductId && objCart[item].count > 0) {
          billProduct.push(objCart[item]);
          break;
        }
      }
    }
    var price = '{"totalPrice":"' + this.data.totalprice + '","dikou":{"id":"' + this.data.dikouid + '","title":"' + this.data.dikoutitle + '","price":"' + this.data.dikouprice + '","color":"' + this.data.dikoucolor + '"},"hongbao":{"title":"' + this.data.hongbaotitle + '","price":"' + this.data.hongbaoprice + '","color":"' + this.data.hongbaocolor + '"},"finalProductPrice":"' + this.data.finalProductPrice + '","yunfei":"' + this.data.yunfei + '","finalPrice":"' + this.data.finalPrice + '","fvipamount":"' + this.data.fvipamount + '","totalpricevip":"' + this.data.totalpricevip + '","viptext":"' + this.data.viptext + '","payprice":"' + this.data.payprice + '"}';
    var newObjCart = new Array();
    var has = false;
    for (var item in objCart) {
      if (objCart[item].count > 0) {
        newObjCart.push(objCart[item]);
        has = true;
      }
    }
    if (has) {
      wx.setStorageSync('cartProduct', newObjCart)
      wx.setStorageSync('billProduct', billProduct)
      wx.setStorageSync('finalprice', price)

      wx.navigateTo({
        url: '/pages/bills/bills',
      })
    } else {
      wx.showModal({
        title: '',
        content: '请选择结算商品',
        showCancel: false,
        confirmColor: '#f60'
      })
    }
  },
  toFloat: function(res) {
    return (isNaN(parseFloat((res + '').replace('￥', '')))) ? 0 : parseFloat((res + '').replace('￥', ''))
  },
  getUserInfo: function(e) {
    if (e.detail.errMsg != 'getUserInfo:fail auth deny') {
      this.setData({
        loginView: false
      })
      app.globalData.userInfo = e.detail.userInfo
      this.setData({
        userInfo: e.detail.userInfo,
        hasUserInfo: true
      })

    }
  },
  hideView: function() {
    this.setData({
      loginView: false
    })
  },
  chooseAll: function() {
    checkProductId = new Array();
    for (var item in objCart) {
      objCart[item].checked = !this.data.selectedAllStatus;
      // checkProductId += objCart[item].fProductId + ',';

      checkProductId.push(objCart[item].fProductId + '');
    }
    if (this.data.selectedAllStatus) {
      checkProductId = "";
    }
    this.setData({
      selectedAllStatus: !this.data.selectedAllStatus,
      product: objCart
    })
    this.setCartPriceTabBar();
  },
  toDetail: function(res) {
    var productId = res.currentTarget.id;
    wx.setStorage({
      key: 'productDetail',
      data: productId,
    })
    wx.navigateTo({
      url: '/pages/productdetail/productdetail',
    })
  },
  toProduct: function() {
    wx.switchTab({
      url: '/pages/product/product',
    })
  },
  toJoinVip: function(res) {
    // wx.switchTab({
    //   url: '/pages/vip/vip',
    //   success: function(res) {},
    //   fail: function(res) {},
    //   complete: function(res) {},
    // })
    wx.setStorageSync('product-vip', '2')
    wx.navigateTo({
      url: '/pages/joinvip/joinvip',
    })
  },
  getCouponByUser: function() {
    var that = this;
    var dikouInfo = wx.getStorageSync("couponInfo");
    // wx.clearStorageSync("couponInfo");
    if (dikouInfo.split('|').length > 1) {
      this.setData({
        dikouid: dikouInfo.split('|')[0],
        dikoutitle: dikouInfo.split('|')[1],
        dikouprice: dikouInfo.split('|')[2] * 1,
      })
    } else {
      var couponCount = 0;
      wx.request({
        url: app.globalData.appurl + '/wxGetUserCoupon',
        data: {
          "fuserid": app.globalData.userAppInfo.fuserid
        },
        success: function(res) {
          objCoupon = JSON.parse(res.data.massage);


        }
      })
    }
  },
  toHongbao: function() {
    var that = this;

    var newObjCart = new Array();
    for (var item in objCart) {
      if (objCart[item].count > 0) {
        newObjCart.push(objCart[item]);
      }
    }
    wx.setStorageSync('cartProduct', newObjCart)
    if (this.data.hongbaoprice != '无可用抵扣券') {
      wx.setStorageSync("couponPrice", this.data.payprice.replace('￥', ''))
      wx.navigateTo({
        url: '/pages/cartCoupon/cartCoupon',
        success: function(res) {},
        fail: function(res) {},
        complete: function(res) {},
      })
    }
  }

})