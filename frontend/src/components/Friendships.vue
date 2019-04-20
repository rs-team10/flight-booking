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
                <td>{{ props.item.email }}</td>
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
                    text: "Email",
                    align: 'left',
                    sortable: false,
                    value: 'email'
                },
                {
                    text: "Status",
                    align: 'center',
                    sortable: true,
                    value: 'status',
                    width: 170
                }
            ],
            users: []
        };
    },
    methods: {
        searchUsersClicked: function() {
            this.$emit('searchUsersClicked');
        }
    },
    created() {
        this.$axios.get('http://localhost:8081/api/getAllFriends/').then((response) => {
            this.users = response.data;
            console.log(response.data);
        }).catch(function(error) {
                alert(error.response.data.message);
            });
    }
}
</script>