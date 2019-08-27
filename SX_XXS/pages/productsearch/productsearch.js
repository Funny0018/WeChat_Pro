// pages/productsearch/productsearch.js
var app = getApp();
var objCart = new Array();
var key = '';
var currIndex = 1;
var pageSize = 5;
var obj = '';
var maxPage = 0;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    product: '',
    key: '',
    isbottom: false,
    loading: true,
    keys: '',
    name_focus: true,
    input_value: ''
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

    this.currIndex = 1;
    this.pageSize = app.globalData.pageSize;
    objCart = wx.getStorageSync('cartProduct');
    var keys = wx.getStorageSync('searchkeys');
    this.setData({
      keys: keys
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

    wx.setStorageSync("cartProduct", objCart);
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
    if (!this.data.isbottom) {
      this.crruPages = this.crruPages + 1;
      this.getProduct(this.crruPages, this.pagesize, 0);
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  getProduct: function(crruPages, pagesize, key) {
    this.setData({
      loading: false
    })
    var that = this;
    var obj = this.data.product;
    wx.request({
      url: app.globalData.appurl + '/wxGetKeyProduct',
      data: {
        crruPage: crruPages,
        pageSize: pagesize,
        key: key
      },
      success: function(res) {
        var products = JSON.parse(res.data.massage);
        that.maxPage = res.data.maxPage;
        if (that.crruPages < that.maxPage) {
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
              // obj[item].count = "0";
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
  setKey: function(res) {
    this.setData({

      key: res.detail.value
    })
  },
  searchproduct: function(res) {
    var currIndex = this.currIndex;
    var pagesize = this.pageSize;
    var key = this.data.key;
    var keys = wx.getStorageSync('searchkeys');
    if (keys != '') {
      keys = Array.from(keys);
      var index = -1;
      for (var item in keys) {
        if (keys[item] == key) {
          index = item;
        }
      }
      if (index > -1) {
        keys.splice(index, 1)
        keys.unshift(key);
      } else {
        keys.unshift(key);
      }
    } else {
      keys = new Array();
      keys.unshift(key);
    }
    wx.setStorage({
      key: 'searchkeys',
      data: keys,
    })
    this.setData({
      product: ''
    })
    this.getProduct(currIndex, pagesize, key);
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
    this.setData({
      product: obj
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
  bindPlus: function(event) {
    var productId = event.currentTarget.id.split('-')[1];
    var obj = this.data.product;
    var has = 0;
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
    this.setData({
      product: obj
    })
  },
  delete_list: function() {
    this.setData({
      keys: new Array()
    })
    wx.removeStorage({
      key: 'searchkeys',
      success: function(res) {},
    })
  },
  this_value: function(e) {
    this.setData({
      name_focus: true
    })
    let key = e.currentTarget.dataset.text;
    var keys = wx.getStorageSync('searchkeys');
    if (keys != '') {
      keys = Array.from(keys);
      var index = -1;
      for (var item in keys) {
        if (keys[item] == key) {
          index = item;
        }
      }
      if (index > -1) {
        keys.splice(index, 1)
        keys.unshift(key);
      } else {
        keys.unshift(key);
      }
    } else {
      keys = new Array();
      keys.unshift(key);
    }
    wx.setStorage({
      key: 'searchkeys',
      data: keys,
    })
    currIndex = 1;
    pageSize = 5;
    this.getProduct(currIndex, pageSize, key);
    this.setData({
      input_value: key
    })
  }
})