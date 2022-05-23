<template>
  <div>
    <b-table responsive :items="traces" :fields="fields" :hover="true" :outlined="true">
      <template slot="dateHeure" slot-scope="data">
        <a
          :href="`#${data.value.replace(/[^a-z]+/i,'-').toLowerCase()}`"
          @click="showModal(data.item)"
        >{{data.value}}</a>
      </template>
    </b-table>
    <br>
    <b-modal v-model="show" v-if="show" ok-only ok-title="Fermer" :hide-header="true">
      <TraceView v-if="selectedTrace!=null" :trace="selectedTrace"></TraceView>
    </b-modal>
  </div>
</template>

<script>
import TraceView from "./TraceView.vue";
export default {
  props: ["traces"],
  components: {
    TraceView
  },
  data() {
    return {
      fields: [
        { key: "dateHeure", label: "Date Opération" },
        { key: "actionLabel", label: "Action" },
        { key: "user", label: "Utilisateur" },
        { key: "uorg", label: "Unité" },
        { key: "mode" }
      ],
      show: false,
      selectedTrace: null
    };
  },
  methods: {
    showModal(trace) {
      this.selectedTrace = trace;
      this.show = true;
    }
  }
};
</script>


<style>
.margin-table {
  margin-top: 20px;
}
.margin-icon {
  font-size: 21px !important;
  padding: 10px 8px 8px 8px !important;
}
.modal-dialog {
  max-width: 100% !important;
  margin-left: 10px !important;
}
</style>
