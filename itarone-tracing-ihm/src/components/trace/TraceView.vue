<template>
  <div>
    <fieldset class="fieldset">
      <legend class="legend">Détails</legend>
      <b-container>
        <b-row>
          <b-col lg="2">
            <b>Date Opération:</b>
          </b-col>
          <b-col lg="2">{{trace.dateHeure}}</b-col>
          <b-col lg="1">
            <b>Action:</b>
          </b-col>
          <b-col lg="2">{{trace.actionLabel}}</b-col>
          <b-col lg="1">
            <b>Utilisateur:</b>
          </b-col>
          <b-col lg="2">{{trace.user}}</b-col>
          <b-col lg="1">
            <b>Unité:</b>
          </b-col>
          <b-col lg="1">{{trace.uorg}}</b-col>
        </b-row>
      </b-container>
    </fieldset>
    <br>
    <JsonCompare
      v-for="(item, index) in args"
      :key="index"
      :after="JSON.stringify(item)"
      :before="getBeforeElement(index)"
      :edit="showEdit"
    ></JsonCompare>
  </div>
</template>
<script>
import JsonCompare from "../jsonCompare/JsonCompare.vue";
export default {
  props: {
    trace: {
      type: String,
      default: `{ argsBefore: [], argsAfter: [], mode: "" }`
    }
  },
  components: {
    JsonCompare
  },
  methods: {
    getBeforeElement(index) {
      if (this.trace.mode == "edit") {
        let PARSED_STRING = JSON.parse(this.trace.argsBefore);
        return JSON.stringify(PARSED_STRING[index]);
      } else {
        return "";
      }
    }
  },
  computed: {
    args() {
      return JSON.parse(this.trace.argsAfter);
    },
    showEdit() {
      if ("edit" == this.trace.mode) {
        return true;
      }
      return false;
    }
  }
};
</script>
<style>
.legend {
  color: #39c;
  font-style: italic;
  padding-left: 12px;
  padding-right: 12px;
  font-size: 1rem;
  width: 75px;
}
.fieldset {
  display: block;
  padding-inline-start: 0.75em;
  padding-inline-end: 0.75em;
  padding-block-end: 0.625em;
  min-inline-size: min-content;
  border: 2px solid #39c;
  -moz-border-radius: 8px;
  -webkit-border-radius: 8px;
  border-radius: 8px;
}
</style>
