// pages/product/product.js
var product = "";
var objCart = new Array();
var obj = '';
var totalcount = 0;
var appInstance = getApp();
var crruPages = 1;
var pageSize = 10;
var QQMapWX = require('../../static/script/qqmap-wx-jssdk.min.js');
var qqmapsdk;
var maxPage = 0;
var finger = {};
var busPos = {};
var linePos = {};
var windowsWidth = 0;
var interval = null;
var scrollTop = 0;
Page({

  /**
   * 页面的初始数据
   */

  data: {
    userInfo: {},
    inputaddress: "",
    product: "",
    productCart: "",
    address: "未获取到当前位置",
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    producttypeList: JSON.parse('[]'),
    producttypeListTwo: JSON.parse('[]'),
    producttypeListTwoShow: false,
    productType: 0,
    productTypeTwo: 0,
    isTypeTwo: false,
    showCon: false,

    hide_good_box: true,
    bus_x: 0,
    bus_y: 0,
    isbottom: false,
    loading: true,

    isvip: "普通用户",
    joinVip: '立即开通',
    vipdate: '',
    showIndex: 'display',

    top: 0,
    top1: 0,
    hotobj: '',
    productshow: 'none',
    indexshow: 'display',
    toshow: 'none',

    menu1Obj: '',
    menu2Obj: '',
    tejiaObj: '',
    //公告
    text: "配送时间早8：00-晚8:00点，出现质量问题包退换！",
    marqueePace: 1, //滚动速度
    marqueeDistance: 0, //初始滚动距离
    marquee_margin: 30,
    size: 14,
    interval: 20, // 时间间隔，
    //购物车提示

    totalPrice: '',
    infoPrice: '',
    dialogShow: 'none',
    dialogTopShow: 'display',
    shareId: 0,
    //新人红包
    newcoupon: false,
    opentejia: false,
    newPage: 0

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    var show = options.show;
    if (show == "share") {
      wx.navigateTo({
        url: '/pages/share/share?shareid=' + options.shareid,
      })
    }
    that.setData({
      shareId: options.userId
    })
    var top = 0;
    this.windowsWidth = wx.getSystemInfoSync().windowWidth;
    this.busPos = {};
    this.busPos['X'] = 160;
    this.busPos['Y'] = appInstance.globalData.wh - 20;

    if (appInstance.globalData.userInfo) {
      this.setData({
        userInfo: appInstance.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      appInstance.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          appInstance.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
    this.setData({
      top: this.toPX(-210)
    })
    var that = this;
    var timeOut = setTimeout(function() {
      that.setData({
        top: that.toPX(-340),
      })
    }, 1000);
    wx.request({
      url: appInstance.globalData.appurl + '/wxGetProductType',
      success: function(res) {
        if (res.data.massage.indexOf('99999') >= 0) {
          that.setData({
            opentejia: true,
          })
        } else {
          that.setData({
            opentejia: false,
          })

        }
        that.setData({
          producttypeList: JSON.parse('' + res.data.massage),
          producttypeListTwo: JSON.parse('[]'),
        })
      }
    })
    this.pagesize = appInstance.globalData.pageSize;
    this.crruPages = 1;
    this.maxPage = 0;
    this.getProduct(this.crruPages, this.data.productType);
    this.getHotProduct();
    this.getmenu1Obj();
    this.getmenu2Obj();
    this.getTejia();

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.setData({
      loading: false,

      // productshow: 'none',
      // indexshow: 'display',
      // isbottom: true,
      // productType: 0,
      // producttypeListTwo: JSON.parse('[]'),
      // top: this.toPX(-180),
      // dialogTopShow: 'display'
    })
    totalcount = 0;
    var that = this;
    var timeOut = setTimeout(function() {
      that.setData({
        top: that.toPX(-340),
      })
    }, 1000);
    wx.getSetting({
      success: (res) => {
        if (!res.authSetting['scope.userLocation'] && res.authSetting['scope.userLocation'] != undefined) {
          //打开提示框，提示前往设置页面
          that.setData({
            showCon: true
          })
        } else {
          that.setData({
            showCon: false
          })
        }
      }
    })



    if (!appInstance.globalData.address) {
      qqmapsdk = new QQMapWX({
        key: '7GJBZ-P5Q3X-J744P-T35UR-MNXA5-WZFUM'
      });
      var that = this;
      wx.getLocation({
        type: 'wgs84',
        success: function(res) {
          qqmapsdk.reverseGeocoder({
            location: {
              latitude: res.latitude,
              longitude: res.longitude
            },
            success: function(addressRes) {
              var address = addressRes.result.formatted_addresses.recommend;
              if (address == '') {
                address = '未获取到当前位置'
              }
              appInstance.globalData.address = address;
              that.setData({
                address: address
              })
            },
            fail: function(res) {}

          })
        },
        fail: function(res) {
          that.setData({
            address: "未获取到当前位置"
          })
        }
      })
    }
    var length = that.data.text.length * that.data.size; //文字长度
    var windowWidth = wx.getSystemInfoSync().windowWidth; // 屏幕宽度
    that.setData({
      length: length,
      windowWidth: windowWidth
    });
    this.setData({
      address: appInstance.globalData.address,

      // product: ""
    })
    var that = this;
    // this.crruPages = 1;
    // this.maxPage = 0;
    // totalcount = 0;
    objCart = wx.getStorageSync('cartProduct');
    this.isSalled();
    var obj = this.data.product;
    for (var item in obj) {
      if (objCart.length > 0) {
        for (var itemcart in objCart) {
          if (obj[item].fProductId == objCart[itemcart].fProductId) {
            obj[item].count = objCart[itemcart].count;
            break;
          } else {
            obj[item].count = "0";
          }
        }
      } else {
        obj[item].count = "0";
      }
    }
    this.setData({
      product: obj
    })

    this.pages = 1;

    for (var itemcart in objCart) {
      totalcount += objCart[itemcart].count;
    }
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
    if (this.data.address == "") {
      this.setData({
        address: "未获取到当前位置"
      })
    }
    var userinfo = "";

    // if (appInstance.globalData.userAppInfo != null && appInstance.globalData.userAppInfo != '') {

    appInstance.getUserInfoForApp().then(function(res) {
      if (res.status == 200) {
        userinfo = appInstance.globalData.userAppInfo;
        if (userinfo.falertinfo != 'null' && userinfo.falertinfo != "") {
          wx.showModal({
            title: '分享活动',
            content: userinfo.falertinfo,
            showCancel: false,
            confirmColor: '#f60',
            success: function() {
              wx.request({
                url: appInstance.globalData.appurl + '/setAlertInfo',
                data: {
                  "userid": userinfo.fuserid
                },
                success: function(res) {}
              })
            }
          })
        }
        wx.setStorageSync("userAppInfo", userinfo);
        var isvip = 0;
        if (userinfo != '') {
          isvip = userinfo.fisvip + appInstance.globalData.bayVip;
        }
        var vipdate = '';
        var joinVip = '立即开通';
        if (isvip > 0) {
          isvip = '尊享黑金会员';
          vipdate = '您的会员还剩' + appInstance.globalData.days + '天到期';
          joinVip = '立即续费';
        } else {
          isvip = '普通用户';
          joinVip = '立即开通';
          vipdate = '限时1元会员体验';
        }
        var fnewcoupon = false;
        if (userinfo.fnewcoupon != 2) {
          fnewcoupon = true;
        }
        // that.setCartAndTabBar();
        that.setData({
          isVip: isvip,
          vipdate: vipdate,
          joinVip: joinVip,
          loading: true,
          newcoupon: fnewcoupon
        })
      }
    })
    // that.scrolltxt(); // 第一
    that.setCartAndTabBar();
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
    wx.setStorage({
      key: 'cartProduct',
      data: newObjCart,
    })
    wx.setStorageSync("cartProduct", newObjCart);
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

    this.setData({
      product: '',
      isbottom: false
    })
    this.crruPage = 1;
    if (this.data.isTypeTwo) {
      this.getProduct(1, this.data.productTypeTwo)
    } else {
      this.getProduct(1, this.data.productType)
    }
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    // if (this.crruPages <= this.maxPage || this.maxPage * 1 == 0) {
    if (!this.data.isbottom) {
      this.crruPages = this.crruPages + 1;
      if (this.data.isTypeTwo) {
        this.getProduct(this.crruPages, this.data.productTypeTwo)
      } else {
        this.getProduct(this.crruPages, this.data.productType)
      }
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  bindMinus: function(event) {
    var productId = event.target.id.split('-')[1];
    var obj = this.data.product;
    var count = 0;
    for (var item in obj) {
      if (obj[item].fProductId == productId) {
        obj[item].count = obj[item].count * 1 - 1;
        if (obj[item].count < 0) {
          obj[item].count = 0;
        }
        break;
      }
    }
    for (var item in objCart) {
      if (objCart[item].fProductId == productId) {
        objCart[item].count = objCart[item].count * 1 - 1;
        if (objCart[item].count < 0) {
          objCart[item].count = 0;
        }
        break;
      }
    }
    var hotobj = this.data.hotobj;
    for (var item in hotobj) {
      if (hotobj[item].fProductId == productId) {
        hotobj[item].count = hotobj[item].count * 1 - 1;
        if (hotobj[item].count < 0) {
          hotobj[item].count = 0;
        }
      }
    }
    this.setCartAndTabBar();
    this.setData({
      product: obj,
      hotobj: hotobj
    })
  },
  bindPlus: function(event) {
    //购物车动画
    this.finger = {};
    var topPoint = {};
    this.finger['X'] = event.changedTouches['0'].clientX;
    this.finger['Y'] = event.changedTouches['0'].clientY;
    if (this.finger['X'] > this.busPos['x']) {
      topPoint['X'] = (this.finger['X'] - this.busPos['X']) / 2 + this.busPos['X'];
    } else {
      topPoint['X'] = (this.busPos['X'] - this.finger['X']) / 2 + this.finger['X'];
    }
    if (this.finger['Y'] < this.busPos['Y']) {
      topPoint['Y'] = this.finger['Y'] - 80;
    } else {
      topPoint['Y'] = this.busPos['Y'] - 80;
    }

    this.linePos = appInstance.bezier([this.busPos, topPoint, this.finger], 15);
    this.srartAnimation();
    //购物车计算
    var productId = event.currentTarget.id.split('-')[1];
    // var obj = (this.data.product).concat(this.data.menu1Obj).concat(this.data.menu2Obj);
    var obj = null;
    if (event.currentTarget.id.split('-')[0] == 'cart1') {
      obj = this.data.menu1Obj;
    } else if (event.currentTarget.id.split('-')[0] == 'cart2') {
      obj = this.data.menu2Obj
    } else {
      obj = this.data.product
    }
    var has = 0;
    var objHas = 0;
    for (var item in obj) {
      if (obj[item].fProductId == productId) {
        obj[item].count = obj[item].count * 1 + 1;
      }
    }
    for (var item in objCart) {
      if (objCart[item].fProductId == productId) {
        has = 1;
      }
    }
    if (has == 0) {
      for (var item in obj) {
        if (obj[item].fProductId == productId) {
          objHas = 1;
          if (objCart == '') {
            objCart = new Array();
            objCart.push(obj[item]);
          } else {
            // objCart.push({
            //   "fProductId": obj[item].fProductId,
            //   "fImgUrl": obj[item].fImgUrl,
            //   "count": obj[item].count
            // });
            objCart.push(obj[item]);
          }
        }
      }
    } else {
      for (var item in objCart) {
        if (objCart[item].fProductId == productId) {
          objCart[item].count = objCart[item].count * 1 + 1;
        }
      }
    }


    var hotobj = this.data.hotobj;
    if (has == 0 && objHas == 0) {
      for (var item in hotobj) {
        if (hotobj[item].fProductId == productId) {
          if (objCart == '') {
            objCart = new Array();
            objCart.push(hotobj[item]);
          } else {
            // objCart.push({
            //   "fProductId": obj[item].fProductId,
            //   "fImgUrl": obj[item].fImgUrl,
            //   "count": obj[item].count
            // });
            objCart.push(hotobj[item]);
          }
        }
      }
    }

    for (var item in hotobj) {
      if (hotobj[item].fProductId == productId) {
        hotobj[item].count = hotobj[item].count * 1 + 1;
      }
    }
    this.setData({
      product: obj,
      hotobj: hotobj
    })

    this.setCartAndTabBar();
  },
  setCartAndTabBar: function() {
    var that = this;
    var totalcount = 0;
    // objCart = new Array();
    for (var item in objCart) {
      totalcount += objCart[item].count;
    }
    if (totalcount > 0) {
      wx.setTabBarBadge({
        index: 1,
        text: totalcount + '',
      })
    } else {
      wx.removeTabBarBadge({
        index: 1,
      })
    }
    var retPriceInfo = "";
    var newpage = 0;
    newpage = this.data.newPage;
    console.log(newpage)
    if (newpage > 0) {
      newpage += 1;
      retPriceInfo = appInstance.cartForDetail(objCart).split(',');
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
      that.setData({
        totalPrice: "￥" + retPriceInfo[0],
        infoPrice: priceInfo,
        dialogShow: dialogShow,
        newPage: newpage,
      })
    } else {
      newpage += 1;
      appInstance.getBaseConfigForMain().then(function(res) {
        if (res.status == 200) {
          console.log(appInstance.globalData)
          retPriceInfo = appInstance.cartForDetail(objCart).split(',');
          console.log(retPriceInfo);
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
          that.setData({
            totalPrice: "￥" + retPriceInfo[0],
            infoPrice: priceInfo,
            dialogShow: dialogShow,
            newPage: newpage,
          })
        }
      })

    }

  },
  toAddress: function(optins) {
    wx.navigateTo({
      url: '/pages/address/address'
    })
  },
  getUserInfo: function(e) {
    appInstance.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  getProduct: function(crruPages, type) {
    this.setData({
      loading: false
    })
    var that = this;
    var obj = this.data.product;
    wx.request({
      url: appInstance.globalData.appurl + '/wxGetDefaultProduct',
      data: {
        crruPage: crruPages,
        pageSize: this.pagesize,
        type: type
      },
      success: function(res) {
        var products = JSON.parse(res.data.massage.replace('\\', '/'));

        that.maxPage = res.data.maxPage;
        for (var item in products) {
          var toptap = products[item].ftoptag;
          if (toptap == 'null') {
            products[item].ftoptag = '';
          } else if (toptap == '') {
            products[item].ftoptag = '';
          } else {
            products[item].ftoptag = toptap.split(',');
          }
        }

        if (that.crruPages <= that.maxPage) {
          that.setData({
            isbottom: false
          })
        } else {
          that.setData({
            isbottom: true
          })
        }
        if (obj == '') {
          obj = products;
        } else {
          for (var item in products) {
            obj.push(products[item]);
          }
        }
        for (var item in obj) {
          for (var itemcart in objCart) {
            if (obj[item].fProductId == objCart[itemcart].fProductId) {
              obj[item].count = objCart[itemcart].count;
              break;
            } else {
              obj[item].count = "0";
            }
          }
        }
        that.setData({
          product: obj,
          loading: true
        })
      }
    })
  },
  getHotProduct: function(crruPages) {
    this.setData({
      loading: false
    })
    var that = this;
    var hotobj = this.data.hotobj;
    wx.request({
      url: appInstance.globalData.appurl + '/wxGetHotProduct',
      success: function(res) {
        var products = JSON.parse(res.data.massage.replace('\\', '/'));

        for (var item in products) {
          var toptap = products[item].ftoptag;
          if (toptap == 'null') {
            products[item].ftoptag = '';
          } else if (toptap == '') {
            products[item].ftoptag = '';
          } else {
            products[item].ftoptag = toptap.split(',');
          }
        }
        hotobj = products;
        for (var item in hotobj) {
          for (var itemcart in objCart) {
            if (hotobj[item].fProductId == objCart[itemcart].fProductId) {
              hotobj[item].count = objCart[itemcart].count;
              break;
            } else {
              hotobj[item].count = "0";
            }
          }
        }
        that.setData({
          loading: true,
          hotobj: hotobj
        })
      }
    })
  },
  getmenu1Obj: function(type) {
    this.setData({
      loading: false
    })
    var that = this;
    var menu1Obj = this.data.menu1Obj;
    wx.request({
      url: appInstance.globalData.appurl + '/wxGeTypeProduct',
      data: {
        type: '12',
      },
      success: function(res) {
        var products = JSON.parse(res.data.massage.replace('\\', '/'));
        for (var item in products) {
          var toptap = products[item].ftoptag;
          if (toptap == 'null') {
            products[item].ftoptag = '';
          } else if (toptap == '') {
            products[item].ftoptag = '';
          } else {
            products[item].ftoptag = toptap.split(',');
          }
        }
        menu1Obj = products;

        that.setData({
          loading: true,
          menu1Obj: menu1Obj,

          isbottom: false,
        })
      }
    })
  },
  getmenu2Obj: function(type) {
    this.setData({
      loading: false
    })
    var that = this;
    var menu2Obj = this.data.menu2Obj;
    wx.request({
      url: appInstance.globalData.appurl + '/wxGeTypeProduct',
      data: {
        type: '14',
      },
      success: function(res) {
        var products = JSON.parse(res.data.massage.replace('\\', '/'));
        for (var item in products) {
          var toptap = products[item].ftoptag;
          if (toptap == 'null') {
            products[item].ftoptag = '';
          } else if (toptap == '') {
            products[item].ftoptag = '';
          } else {
            products[item].ftoptag = toptap.split(',');
          }
        }


        menu2Obj = products;
        // for (var item in menu1Obj) {
        //   for (var itemcart in objCart) {
        //     if (hotobj[item].fProductId == objCart[itemcart].fProductId) {
        //       hotobj[item].count = objCart[itemcart].count;
        //       break;
        //     } else {
        //       hotobj[item].count = "0";
        //     }
        //   }
        // }
        that.setData({
          loading: true,
          menu2Obj: menu2Obj,
          isbottom: false,
        })
      }
    })
  },
  getTejia: function(type) {
    this.setData({
      loading: false
    })
    var that = this;
    var menu2Obj = this.data.menu2Obj;
    wx.request({
      url: appInstance.globalData.appurl + '/wxGetDefaultProductTejia',
      success: function(res) {
        var products = JSON.parse(res.data.massage.replace('\\', '/'));
        for (var item in products) {
          var toptap = products[item].ftoptag;
          if (toptap == 'null') {
            products[item].ftoptag = '';
          } else if (toptap == '') {
            products[item].ftoptag = '';
          } else {
            products[item].ftoptag = toptap.split(',');
          }
        }


        var tejiaObj = products;
        // for (var item in menu1Obj) {
        //   for (var itemcart in objCart) {
        //     if (hotobj[item].fProductId == objCart[itemcart].fProductId) {
        //       hotobj[item].count = objCart[itemcart].count;
        //       break;
        //     } else {
        //       hotobj[item].count = "0";
        //     }
        //   }
        // }
        that.setData({
          loading: true,
          tejiaObj: tejiaObj,
          isbottom: false,
        })
      }
    })
  },
  chageType: function(res) {
    var producttype = res.target.id;
    var typelist = this.data.producttypeList;
    var newtypelist = this.data.producttypeListTwo;
    var showtwo = false;
    var top1 = 0;
    for (var item in typelist) {
      if (typelist[item].ftypeid == producttype) {
        if (typelist[item].level == '1') {
          newtypelist = typelist[item].fchildid
          if (typelist[item].fhaschild == "true") {
            showtwo = true;
            top1 = this.toPX(70);
          }
        }
      }
    }
    this.setData({
      productTypeTwo: producttype,
      productType: producttype,
      product: '',
      producttypeListTwo: newtypelist,
      producttypeListTwoShow: showtwo,
      top1: top1
    })
    if (producttype == 0) {
      this.setData({
        productshow: 'none',
        indexshow: 'display',
        toshow: 'none',
        isTypeTwo: false
      })
    } else {
      this.setData({
        productshow: 'display',
        indexshow: 'none',
        toshow: 'none',
        isTypeTwo: false
      })
    }
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productType);
  },
  chageTypeTwo: function(res) {
    var producttype = res.target.id;
    this.setData({
      productTypeTwo: producttype,
      product: '',
      isTypeTwo: true
    })
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productTypeTwo);
  },
  toSearch: function(res) {
    wx.navigateTo({
      url: '/pages/productsearch/productsearch',
    })
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
  ToptoDetail1: function(res) {
    var productId = res.currentTarget.id;
    wx.setStorage({
      key: 'productDetail',
      data: 226,
    })
    wx.navigateTo({
      url: '/pages/productdetail/productdetail',
    })
  },
  srartAnimation: function(e) {
    var index = 0,
      that = this,
      bezer_points = that.linePos['bezier_points'];
    this.setData({
      hide_good_box: false,
      bus_x: that.finger['X'],
      bus_y: that.finger['Y']
    });
    index = bezer_points.length;
    this.timer = setInterval(function() {
      for (let i = index - 1; i > -1; i--) {
        that.setData({
          bus_x: bezer_points[i]['X'],
          bus_y: bezer_points[i]['Y']

        })

        if (i < 1) {
          clearInterval(that.timer);
          that.setData({
            hide_good_box: true

          })

        }
      }
    }, 15);

  },
  changeModalCancel: function() {
    this.setData({
      hasUserInfo: false,

    })
  },
  joinVip: function() {
    wx.setStorageSync('product-vip', 1)
    wx.navigateTo({
      url: '/pages/joinvip/joinvip',
    })
  },

  handletouchstart: function(event) {
    this.data.lastX = event.touches[0].pageX;
    this.data.lastY = event.touches[0].pageY;
    // clearInterval(this.interval);
  },
  handletouchmove: function(event) {
    if (scrollTop <= 5) {
      var currentX = event.touches[0].pageX
      var currentY = event.touches[0].pageY
      var tx = currentX - this.data.lastX
      var ty = currentY - this.data.lastY
      var top = this.data.top;
      var max = this.toPX(-180);
      if (ty > 0 && top < max) {
        top = top + ty;
      }
      if (top >= max) {
        top = max;
      }
      this.setData({
        top: top,
      })
      this.data.lastX = currentX;
      this.data.lastY = currentY;
    }
  },
  //滑动结束事件
  handletouchend: function(event) {
    this.data.currentGesture = 0;
    this.setData({
      top: this.toPX(-345)
    });


  },
  toPX: function(rpx) {
    return rpx / 750 * this.windowsWidth;
  },
  showproduct: function(res) {
    this.setData({
      productshow: 'display',
      indexshow: 'none',
      toshow: 'none',
    })
    this.setData({
      productType: 12,
      product: ''
    })
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productType);
  },
  showType12: function() {
    this.setData({
      productshow: 'display',
      indexshow: 'none',
      toshow: 'none',
    })
    this.setData({
      productType: 12,
      product: ''
    })
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productType);
  },
  showType13: function() {
    this.setData({
      productshow: 'display',
      indexshow: 'none',
      toshow: 'none',
    })
    this.setData({
      productType: 14,
      product: ''
    })
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productType);
  },
  showType9999: function() {
    this.setData({
      productshow: 'display',
      indexshow: 'none',
      toshow: 'none',
    })
    this.setData({
      productType: 99999,
      product: ''
    })
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productType);
  },
  onTabItemTap(item) {
    this.getHotProduct();
    if (item.text == '首页') {
      this.setData({
        productshow: 'none',
        indexshow: 'display',
        toshow: 'none',
        productType: 0,
        producttypeListTwo: JSON.parse('[]'),
        dialogTopShow: 'display',
        top1: 0,
        producttypeListTwoShow: false
      })
    }
  },
  showToShow: function(res) {
    this.setData({
      toshow: 'display',
    })
  },
  cancelToSHow: function() {
    this.setData({
      toshow: 'none',
    })
  },
  isSalled: function() {
    var newobj = new Array();
    var oldobj = objCart;
    if (oldobj.length > 0) {
      var oldid = "";
      for (var i in oldobj) {
        oldid += oldobj[i].fProductId + ",";
      }
      wx.request({
        url: appInstance.globalData.appurl + '/wxRecheckCart',
        data: {
          ids: oldid
        },
        success: function(res) {
          var newid = JSON.parse(res.data.massage.replace('\\', '/'));
          for (var i in oldobj) {
            for (var j in newid) {
              if (oldobj[i].fProductId == newid[j].fProductId) {
                newid[j].count = oldobj[i].count;
                newid[j].checked = oldobj[i].checked;
                newobj.push(newid[j]);
                break;
              }
            }
          }
          objCart = newobj;
        }
      })
    }
  },
  scrolltxt: function() {
    var that = this;
    var length = that.data.length; //滚动文字的宽度
    var windowWidth = that.data.windowWidth; //屏幕宽度
    if (length > windowWidth) {
      this.interval = setInterval(function() {
        var maxscrollwidth = length + that.data.marquee_margin; //滚动的最大宽度，文字宽度+间距，如果需要一行文字滚完后再显示第二行可以修改marquee_margin值等于windowWidth即可
        var crentleft = that.data.marqueeDistance;
        if (crentleft < maxscrollwidth) { //判断是否滚动到最大宽度
          that.setData({
            marqueeDistance: crentleft + that.data.marqueePace
          })
        } else {
          that.setData({
            marqueeDistance: 0 // 直接重新滚动
          });
          clearInterval(that.interval);
          that.scrolltxt();
        }
      }, that.data.interval);
    } else {
      that.setData({
        marquee_margin: "1000"
      }); //只显示一条不滚动右边间距加大，防止重复显示
    }
  },
  onPageScroll: function(ev) {
    //当滚动的top值最大或最小时，为什么要做这一步是因为在手机实测小程序的时候会发生滚动条回弹，所以为了处理回弹，设置默认最大最小值
    scrollTop = ev.scrollTop
    //判断浏览器滚动条上下滚动
  },
  dialogTopClose: function() {
    this.setData({
      dialogTopShow: 'none'
    })
  },
  TopToProductType1: function() {
    var producttype = 14;
    var typelist = this.data.producttypeList;
    var newtypelist = this.data.producttypeListTwo;
    var showtwo = false;
    var top1 = 0;
    for (var item in typelist) {
      if (typelist[item].ftypeid == producttype) {
        if (typelist[item].level == '1') {
          newtypelist = typelist[item].fchildid
          if (typelist[item].fhaschild == "true") {
            showtwo = true;
            top1 = this.toPX(70);
          }
        }
      }
    }
    this.setData({
      productTypeTwo: producttype,
      productType: producttype,
      product: '',
      producttypeListTwo: newtypelist,
      producttypeListTwoShow: showtwo,
      top1: top1
    })
    if (producttype == 0) {
      this.setData({
        productshow: 'none',
        indexshow: 'display',
        toshow: 'none',
        isTypeTwo: false
      })
    } else {
      this.setData({
        productshow: 'display',
        indexshow: 'none',
        toshow: 'none',
        isTypeTwo: false
      })
    }
    var producttypeTwo = 29;
    this.setData({
      productTypeTwo: producttypeTwo,
      product: '',
      isTypeTwo: true
    })
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productTypeTwo);
  },
  TopToProductType2: function() {

    var producttype = 14;
    var typelist = this.data.producttypeList;
    var newtypelist = this.data.producttypeListTwo;
    var showtwo = false;
    var top1 = 0;
    for (var item in typelist) {
      if (typelist[item].ftypeid == producttype) {
        if (typelist[item].level == '1') {
          newtypelist = typelist[item].fchildid
          if (typelist[item].fhaschild == "true") {
            showtwo = true;
            top1 = this.toPX(70);
          }
        }
      }
    }
    this.setData({
      productTypeTwo: producttype,
      productType: producttype,
      product: '',
      producttypeListTwo: newtypelist,
      producttypeListTwoShow: showtwo,
      top1: top1
    })
    if (producttype == 0) {
      this.setData({
        productshow: 'none',
        indexshow: 'display',
        toshow: 'none',
        isTypeTwo: false
      })
    } else {
      this.setData({
        productshow: 'display',
        indexshow: 'none',
        toshow: 'none',
        isTypeTwo: false
      })
    }
    var producttypeTwo = 28;
    this.setData({
      productTypeTwo: producttypeTwo,
      product: '',
      isTypeTwo: true
    })
    this.crruPages = 1;
    this.getProduct(crruPages, this.data.productTypeTwo);
  },
  toShare: function() {
    // wx.navigateTo({
    //   url: '/pages/award/award',
    // })
    wx.navigateTo({
      url: '/pages/share/share',
    })
  },
  getNewCoupon: function() {
    var that = this;
    wx.showModal({
      title: '',
      content: '领取新人红包成功！',
      showCancel: false,
      confirmColor: '#f60',
      success: function() {
        var userinfo = appInstance.globalData.userAppInfo;
        wx.request({
          url: appInstance.globalData.appurl + '/getNewCoupon',
          data: {
            userid: userinfo.fuserid
          },
          success: function(res) {}
        })
        that.setData({
          newcoupon: false
        })
      }
    })
  }
})