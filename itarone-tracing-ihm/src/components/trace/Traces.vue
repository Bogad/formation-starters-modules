<template>
<div>
  <b-container>
    <br/>
      <b-row class="justify-content-md-end">
                  <b-col col lg="1">
                    <b-button v-if="$keycloak.authenticated" v-on:click="logout()" id="logout">
                        <font-awesome-icon class="mr-2" icon="sign-out-alt" />
                        <span>Déconnexion</span></b-button>
                  </b-col>
                 
      </b-row>
              </b-container>
  <router-view></router-view>
</div>
</template>

<script>
export default {
  methods: {
    logout() {
      this.$keycloak.logoutFn();
      this.$keycloak.push("/");
    }
  }
};
</script>
