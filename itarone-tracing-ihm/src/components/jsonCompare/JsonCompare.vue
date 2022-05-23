<template>
  <div>
    <div class="grid-container editHeader" v-if="edit">
      <div class="editHeader">Avant</div>
      <div class="editHeader" v-if="edit">Après</div>
    </div>
    <div class="grid-container">
      <div class="left" v-if="edit">
        <div ref="before" class="before"></div>
      </div>
      <div class="right">
        <div ref="after" class="after"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as jsonpatch from "fast-json-patch";
import jsonView from "./utils/jsonview.js";
import codes from "./utils/codes.js";

export default {
  props: {
    edit: {
      type: Boolean,
      default: false
    },
    name: {
      type: String,
      default: "Object"
    },
    before: {
      type: String
    },
    after: {
      type: String
    }
  },
  methods: {
    showEdit() {
      if ("edit" == this.edit) {
        return true;
      }
      return false;
    },
    compare() {
      let before = JSON.parse(this.before);
      let after = JSON.parse(this.after);
      const delta = jsonpatch.compare(before, after);
      delta.forEach(diff => {
        if (diff.op === "remove") {
          let beforeValue = jsonpatch.getValueByPointer(before, diff.path);
          let operation = {
            op: "replace",
            path: diff.path,
            value: this.addCode(beforeValue, codes.deleteCode)
          };
          before = jsonpatch.applyOperation(before, operation).newDocument;
        } else if (diff.op === "add") {
          let operation = {
            op: "replace",
            path: diff.path,
            value: this.addCode(diff.value, codes.addCode)
          };
          after = jsonpatch.applyOperation(after, operation).newDocument;
        } else if (diff.op === "replace") {
          let beforeValue = jsonpatch.getValueByPointer(before, diff.path);
          let afterValue = jsonpatch.getValueByPointer(after, diff.path);
          let op1 = {
            op: "replace",
            path: diff.path,
            value: this.addCode(beforeValue, codes.replaceCode)
          };
          let op2 = {
            op: "replace",
            path: diff.path,
            value: this.addCode(afterValue, codes.replaceCode)
          };
          before = jsonpatch.applyOperation(before, op1).newDocument;
          after = jsonpatch.applyOperation(after, op2).newDocument;
        }
      });
      // render the tree view
      jsonView.format(before, this.$refs.before, null);
      jsonView.format(after, this.$refs.after, null);
    },
    addCode(value, code) {
      let codedValue = null;
      if (value instanceof Array) {
        codedValue = [...value, code];
      } else if (typeof value === "object") {
        codedValue = { ...value, code };
      } else {
        codedValue = `${value}${code}`;
      }
      return codedValue;
    }
  },
  mounted() {
    if (this.edit) {
      this.compare();
    } else {
      let PARSED_STRING = JSON.parse(this.after);
      jsonView.format(PARSED_STRING, this.$refs.after, null);
    }
  }
};
</script>

<style>
.grid-container {
  position: relative;
  height: 100%;
  width: 100%;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  background-color: black;
  color: white !important;
  text-align: center !important;
}
.editHeader {
  background-color: darkgray;
  font-size: 1.3rem;
  font-family: Arial, x-locale-body, sans-serif;
  font-weight: 400;
  justify-content: center;
  width: 100%;
}
textarea {
  width: 100%;
  height: 100%;
}
.left,
.right {
  font-size: 16px;
  padding: 20px;
  height: 100%;
  display: flex;
  justify-content: center;
}
.right {
  flex: 1;
}
.left {
  width: 50%;
}
.before,
.after {
  width: 100%;
  height: 100%;
}
</style>
