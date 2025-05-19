import axios from "axios";
import moment from "moment";
import db from "./localstorage";
import store from "../store";
import message from "../message/index"

moment.locale("zh-cn");

// 统一配置
let REQUEST = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,
  responseType: "json",
  validateStatus(status) {
    // 200 外的状态码都认定为失败
    return status === 200;
  }
});

// 拦截请求
REQUEST.interceptors.request.use(
  config => {
    // 有 token就带上
    if (store.state.account.token) {
      ///token 多了一层 "" -_-!!
      let token = store.state.account.token;
      if (typeof token == "string") {
        let reg = new RegExp('"', 'g')//g代表全部
        token = token.replace(reg, '');
      }
      config.headers.Authentication = token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 拦截响应
REQUEST.interceptors.response.use(
  config => {
    return config;
  },
  error => {
    if (error.response) {
      let errorMessage =
        error.response.data === null
          ? "系统内部异常，请联系网站管理员"
          : error.response.data.message;
      switch (error.response.status) {
        case 404:
          message.error('资源未找到')
          break;
        case 403:
        case 401:
          debugger
          message.error('权限不足')
          return new Promise((resolve, reject) => {
            db.clear();
            location.reload();
          });
        default:
          message.error(errorMessage)
          break;
      }
    }
    return Promise.reject(error);
  }
);

const request = {
  post(url, params) {
    return REQUEST.post(url, params, {
      transformRequest: [
        params => {
          let result = "";
          Object.keys(params).forEach(key => {
            if (
              !Object.is(params[key], undefined) &&
              !Object.is(params[key], null)
            ) {
              result +=
                encodeURIComponent(key) +
                "=" +
                encodeURIComponent(params[key]) +
                "&";
            }
          });
          return result;
        }
      ],
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    });
  },
  postjson(url, params) {
    return REQUEST.post(url, params, {
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(params)
    });
  },
  put(url, params) {
    return REQUEST.put(url, params, {
      transformRequest: [
        params => {
          let result = "";
          Object.keys(params).forEach(key => {
            if (
              !Object.is(params[key], undefined) &&
              !Object.is(params[key], null)
            ) {
              result +=
                encodeURIComponent(key) +
                "=" +
                encodeURIComponent(params[key]) +
                "&";
            }
          });
          return result;
        }
      ],
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    });
  },
  get(url, params) {
    let _params;
    if (Object.is(params, undefined)) {
      _params = "";
    } else {
      _params = "?";
      for (let key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null) {
          _params += `${key}=${params[key]}&`;
        }
      }
    }
    return REQUEST.get(`${url}${_params}`);
  },
  del(url, params) {
    let _params;
    if (Object.is(params, undefined)) {
      _params = "";
    } else {
      _params = "?";
      for (let key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null) {
          _params += `${key}=${params[key]}&`;
        }
      }
    }
    return REQUEST.delete(`${url}${_params}`);
  },
  export(url, params = {}) {
    // message.loading("导出数据中");
    return REQUEST.post(url, params, {
      transformRequest: [
        params => {
          let result = "";
          Object.keys(params).forEach(key => {
            if (
              !Object.is(params[key], undefined) &&
              !Object.is(params[key], null)
            ) {
              result +=
                encodeURIComponent(key) +
                "=" +
                encodeURIComponent(params[key]) +
                "&";
            }
          });
          return result;
        }
      ],
      responseType: "blob"
    })
      .then(r => {
        const content = r.data;
        const blob = new Blob([content]);
        const fileName = `${new Date().getTime()}_导出结果.xlsx`;
        if ("download" in document.createElement("a")) {
          const elink = document.createElement("a");
          elink.download = fileName;
          elink.style.display = "none";
          elink.href = URL.createObjectURL(blob);
          document.body.appendChild(elink);
          elink.click();
          URL.revokeObjectURL(elink.href);
          document.body.removeChild(elink);
        } else {
          navigator.msSaveBlob(blob, fileName);
        }
      })
      .catch(r => {
        console.error(r);
        //message.error("导出失败");
      });
  },
  download(url, params, filename) {
    //message.loading("文件传输中");
    return REQUEST.post(url, params, {
      transformRequest: [
        params => {
          let result = "";
          Object.keys(params).forEach(key => {
            if (
              !Object.is(params[key], undefined) &&
              !Object.is(params[key], null)
            ) {
              result +=
                encodeURIComponent(key) +
                "=" +
                encodeURIComponent(params[key]) +
                "&";
            }
          });
          return result;
        }
      ],
      responseType: "blob"
    })
      .then(r => {
        const content = r.data;
        const blob = new Blob([content]);
        if ("download" in document.createElement("a")) {
          const elink = document.createElement("a");
          elink.download = filename;
          elink.style.display = "none";
          elink.href = URL.createObjectURL(blob);
          document.body.appendChild(elink);
          elink.click();
          URL.revokeObjectURL(elink.href);
          document.body.removeChild(elink);
        } else {
          navigator.msSaveBlob(blob, filename);
        }
      })
      .catch(r => {
        console.error(r);
        // message.error("下载失败");
      });
  },
  upload(url, params) {
    return REQUEST.post(url, params, {
      headers: {
        "Content-Type": "multipart/form-data;"
      },
    });
  },
  uploadAndDownload(url, params) {
    return REQUEST.post(url, params, {
      headers: {
        "Content-Type": "multipart/form-data"
      },
      responseType: "blob"
    })
      .then(r => {
        const content = r.data;
        const blob = new Blob([content]);
        const fileName = `${new Date().getTime()}_导入结果.xlsx`;
        if ("download" in document.createElement("a")) {
          const elink = document.createElement("a");
          elink.download = fileName;
          elink.style.display = "none";
          elink.href = URL.createObjectURL(blob);
          document.body.appendChild(elink);
          elink.click();
          URL.revokeObjectURL(elink.href);
          document.body.removeChild(elink);
        } else {
          navigator.msSaveBlob(blob, fileName);
        }
      })
      .catch(r => {
        console.error(r);
        // message.error("导出失败");
      });
  }
};

export default request;
