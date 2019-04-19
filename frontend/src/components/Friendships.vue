<template>
    <v-layout column>
        <h2>Friendships</h2>
        <v-text-field
            v-model="search"
            append-icon="search"
            label="Search friends list"
            single-line
        ></v-text-field>

        <v-data-table
            :headers="headers"
            :items="users"
            :search="search">
            <template v-slot:items="props">
                <td>{{ props.item.firstName }}</td>
                <td>{{ props.item.lastName }}</td>
                <td class="text-xs-right">              
                    <v-btn depressed color="yellow" >
                        <span>Pending</span>
                    </v-btn>
                </td>
            </template>
            <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                Your search for "{{ search }}" found no results.
            </v-alert>
        </v-data-table>
        <div class="text-xs-right">
            <v-btn v-on:click="searchUsersClicked()" color="green" dark class="mt-3">Search Users</v-btn>
        </div>
    </v-layout>
</template>

<script>
export default {
    name: "friendships",
    data() {
        return {
            search: '',
            headers: [
                {
                    text: "First Name",
                    align: 'left',
                    sortable: false,
                    value: 'firstName'
                },
                {
                    text: "Last Name",
                    align: 'left',
                    sortable: false,
                    value: 'lastName'
                },
                {
                    text: "Status",
                    align: 'center',
                    sortable: true,
                    value: 'status',
                    width: 170
                }
            ],
            users: [
                {firstName: "FirstName1", lastName: "lastName"},
                {firstName: "FirstName2", lastName: "lastName"},
                {firstName: "FirstName3", lastName: "lastName"},
                {firstName: "FirstName4", lastName: "lastName"},
                {firstName: "FirstName5", lastName: "lastName"},
                {firstName: "FirstName6", lastName: "lastName"},
                {firstName: "FirstName7", lastName: "lastName"}
            ]
        };
    },
    methods: {
        searchUsersClicked: function() {
            this.$emit('searchUsersClicked');
        }
    }
}
</script>