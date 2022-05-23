<template>
  <div id="traceForm">
    <b-container class="justify-content-md-center">
      <div class="title">
        <h2>Recherche Des Traces</h2>
      </div>
      <b-row class="justify-content-md-center">
        <b-col cols="7">
          <div>
            <b-form @submit="onSubmit" @reset="onReset" v-if="show">
              <b-container>
                <b-row class="justify-content-md-center">
                  <b-col col lg="6">
                    <b-form-group id="userInputGroup" label="Utilisateur:" label-for="userInput">
                      <b-form-input
                        id="userInput"
                        type="text"
                        v-model="form.user"
                        placeholder="Nom Utilisateur"
                      ></b-form-input>
                    </b-form-group>
                  </b-col>
                  <b-col col lg="6">
                    <b-form-group id="uorgInputGroup" label="Unité:" label-for="uorgInput">
                      <b-form-input
                        id="uorgInput"
                        type="text"
                        v-model="form.uorg"
                        placeholder="Unité Organisationnelle"
                      ></b-form-input>
                    </b-form-group>
                  </b-col>
                </b-row>
              </b-container>
              <b-container>
                <b-row class="justify-content-md-center">
                  <b-col col lg="6">
                    <b-form-group
                      id="dateFromInputGroup"
                      label="Date Opération du:"
                      label-for="dateFromInput"
                    >
                      <date-picker
                        id="dateFromInput"
                        class="form-control"
                        lang="fr"
                        v-model="form.dateFrom"
                        type="datetime"
                        format="MM/DD/YYYY HH:mm:ss"
                      ></date-picker>
                    </b-form-group>
                  </b-col>
                  <b-col col lg="6">
                    <b-form-group id="dateToInputGroup" label=" À: " label-for="dateToInput">
                      <date-picker
                        id="dateToInputGroup"
                        class="form-control"
                        lang="fr"
                        v-model="form.dateTo"
                        type="datetime"
                        format="MM/DD/YYYY HH:mm:ss"
                      ></date-picker>
                    </b-form-group>
                  </b-col>
                </b-row>
              </b-container>
              <b-container>
                <b-row class="justify-content-md-center">
                  <b-col col lg="6">
                    <b-form-group id="actionInputGroup" label="Action:" label-for="actionInput">
                      <b-form-select
                        id="actionInput"
                        @change="onActionChanged"
                        v-model="form.action"
                      >
                        <template slot="first">
                          <option :value="null" disabled>-- Select action --</option>
                        </template>
                        <option
                          v-for="action in actions"
                          v-bind:value="action.key"
                          :key="action.key"
                        >{{ action.label }}</option>
                      </b-form-select>
                    </b-form-group>
                  </b-col>
                  <b-col col lg="6">
                    <b-form-group
                      id="attributenputGroup"
                      label="Attributs:"
                      label-for="atributeInput"
                    >
                      <b-form-select
                        id="atributeInput"
                        v-model="selectedAttribute"
                        @change="onAttributeChanged"
                      >
                        <template slot="first">
                          <option :value="null" disabled>-- Select attribute --</option>
                        </template>
                        <option
                          v-for="attribute in attributes"
                          v-bind:value="attribute"
                          :key="attribute.key"
                        >{{ attribute.label }}</option>
                      </b-form-select>
                    </b-form-group>
                  </b-col>
                </b-row>
              </b-container>
              <b-container>
                <b-row class="justify-content-md-center">
                  <b-col>
                    <b-form-textarea
                      id="atributeInput"
                      v-model="form.searchQuery"
                      rows="3"
                      max-rows="3"
                      :no-resize="true"
                    >></b-form-textarea>
                  </b-col>
                </b-row>
                <br>
                <b-row class="buttonBar">
                  <b-button :size="buttonSize" @click="appendSeachQuery(' and ')">ET</b-button>
                  <b-button :size="buttonSize" @click="appendSeachQuery(' or ')">OU</b-button>
                  <b-button
                    :size="buttonSize"
                    @click="appendSeachQuery(' Between VALUE1 and VALUE2 ')"
                  >ENTRE</b-button>
                  <b-col col lg="5"/>
                  <b-button :size="buttonSize" @click="appendSeachQuery(' <= ')">&lt;=</b-button>
                  <b-button :size="buttonSize" @click="appendSeachQuery(' < ')">&lt;</b-button>
                  <b-button :size="buttonSize" @click="appendSeachQuery(' = ')">=</b-button>
                  <b-button :size="buttonSize" @click="appendSeachQuery(' > ')">&gt;</b-button>
                  <b-button :size="buttonSize" @click="appendSeachQuery(' >= ')">&gt;=</b-button>
                </b-row>
                <br>
                <b-row class="justify-content-md-center">
                  <b-col col lg="3">
                    <b-button type="submit" variant="primary">Rechercher</b-button>
                  </b-col>
                  <b-col col lg="3">
                    <b-button type="reset" variant="danger">Reset</b-button>
                  </b-col>
                </b-row>
              </b-container>
            </b-form>
          </div>
        </b-col>
      </b-row>
    </b-container>

    <br>
    <b-container class="root">
      <b-row class="justify-content-md-center">
        <b-col col lg="10">
          <b-alert :show="showDismissibleAlert" dismissible fade variant="danger">
            <p>Aucune trace trouvée!...</p>
          </b-alert>
          <TraceTable id="traceTable" :traces="traces" v-if="!showDismissibleAlert"/>
          <b-pagination
            v-if="showPagination"
            align="center"
            :total-rows="totalElements"
            v-model="currentPage"
            :per-page="perpage"
          ></b-pagination>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import TraceTable from "./TraceTable.vue";
import axios from "axios";
import DatePicker from "vue2-datepicker";
import moment from "moment";
import { scroller } from "vue-scrollto/src/scrollTo";

export default {
  name: "app",
  components: {
    DatePicker,
    TraceTable
  },
  data() {
    return {
      apiUrl: process.env.VUE_APP_ROOT_API_URL,
      traces: [],
      buttonSize: "sm",
      selectedAttribute: {},
      form: {
        user: "",
        uorg: "",
        dateFrom: "",
        dateTo: null,
        action: null,
        searchQuery: "",
        codes: {}
      },
      actions: [],
      attributes: [],
      show: true,
      showDismissibleAlert: false,
      perpage: 10,
      totalElements: 0,
      currentPage: 1,
      totalPages: 1
    };
  },
  methods: {
    showAlert() {
      this.showDismissibleAlert = true;
    },
    onSubmit(evt) {
      this.showDismissibleAlert = false;
      evt.preventDefault();
      this.formatDate();
      this.search();
    },
    search() {
      var vm = this;
      this.axios
        .post(
          "trace/search?page=" + this.page + "&size=" + this.perpage,
          this.form
        )
        .then(
          response => {
            this.totalElements = response.data.totalElements;
            this.totalPages = response.data.totalPages;
            vm.traces = response.data.content;
            const srollTo = scroller();
            srollTo("#traceTable");
          },
          error => {
            vm.traces = [];
            if (error.response.status === 404) {
              vm.showAlert();
            }
            console.log(error);
          }
        );
    },
    formatDate() {
      if (this.form.dateFrom && this.form.dateFrom!="Invalid date")
        this.form.dateFrom = moment(this.form.dateFrom).format(
          "DD/MM/YYYY HH:mm:ss"
        );
      if (this.form.dateTo && this.form.dateTo!="Invalid date")
        this.form.dateTo = moment(this.form.dateTo).format(
          "DD/MM/YYYY HH:mm:ss"
        );
    },
    onActionChanged(evt) {
      this.form.searchQuery = "";
      this.codes = {};
      var attributesPath = "actionrefs?action=" + evt;
      this.axios.get(attributesPath).then(
        response => {
          this.attributes = response.data;
        },
        error => {
          console.log(error);
        }
      );
    },
    onAttributeChanged(evt) {
      this.form.searchQuery += "[" + evt.label + "]";
      this.form.codes[evt.label] = evt.key;
    },
    removeDuplicates(myArr, prop) {
      return myArr.filter((obj, pos, arr) => {
        return arr.map(mapObj => mapObj[prop]).indexOf(obj[prop]) === pos;
      });
    },
    loadAttributes() {
      this.codes = {};
      var attributesPath = "refs";
      this.axios.get(attributesPath).then(
        response => {
          this.attributes = this.removeDuplicates(response.data, "key");
        },
        error => {
          console.log(error);
        }
      );
    },
    loadActions() {
      this.axios.get("action").then(
        response => {
          this.actions = response.data;
        },
        error => {
          console.log(error);
        }
      );
    },
    appendSeachQuery(value) {
      this.form.searchQuery += value;
    },
    onReset(evt) {
      evt.preventDefault();
      /* Reset our form values */
      this.form.user = "";
      this.form.uorg = "";
      this.form.dateFrom = null;
      this.form.dateTo = null;
      this.form.action = null;
      this.form.searchQuery = "";
      this.form.codes = {};
      this.selectedAttribute = {};
      this.traces = [];
      this.totalElements = 0;
      this.currentPage = 1;
      this.totalPages = 1;
      const srollTo = scroller();
      srollTo("#traceForm");
    }
  },
  created() {
    this.axios = axios.create({
      baseURL: this.apiUrl
    });
    this.loadActions();
    this.loadAttributes();
  },
  computed: {
    page() {
      return this.currentPage - 1;
    },
    showPagination() {
      return this.totalPages > 1;
    }
  },
  watch: {
    currentPage: function() {
      this.search();
    }
  }
};
</script>

<style>
.title {
  text-align: center;
  width: 100%;
}
.buttonBar * {
  margin: 3px !important;
}
.buttonBar {
  margin: 8px !important;
}
.mx-input {
  display: inline-block;
  width: auto !important;
  height: auto !important;
  padding: 0 !important;
  font-size: inherit !important;
  font-family: "Segoe UI" !important;
  color: #495057 !important;
  border: 0 !important;
  box-shadow: none;
}
.mx-input-append {
  padding: 0 !important;
}
</style>
