module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: "vuetify",
  quotes: ["off", "single"],
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
    "space-before-function-paren": 0,
  },
  parserOptions: {
    parser: "babel-eslint",
  },
};
