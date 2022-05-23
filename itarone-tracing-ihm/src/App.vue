<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
html,
body {
  height: 100%;
}
#op {
  position: absolute;
  top: 20px;
  left: 20px;
}
#app {
  position: relative;
  height: 100%;
  width: 100%;
}

.line {
  margin: 8px 0;
  display: flex;
  padding-right: 8px;
  width: fit-content;
  border-radius: 3px;
  align-items: center;
}

.caret-icon {
  font-size: 18px;
  text-align: center;
  cursor: pointer;
  padding: 5px;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.json-type {
  margin-right: 4px;
  margin-left: 4px;
}

.json-key {
  margin-right: 4px;
  margin-left: 4px;
}

.json-separator {
  color: white;
}

.json-index {
  margin-right: 4px;
  margin-left: 4px;
}

.json-value {
  margin-left: 8px;
  color: white;
}

.json-size {
  margin-right: 4px;
  margin-left: 4px;
}

.hide {
  display: none;
}

.deleted *,
.replaced *,
.added * {
  color: white;
}
.deleted {
  background: #962b2b !important;
}
.replaced {
  background: #b56909;
}
.added {
  background: #3e9911 !important;
}
.changed {
  color: #ffca00 !important;
}
</style>
