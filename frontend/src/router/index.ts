import { createRouter, createWebHistory } from 'vue-router'

import CalendarList from '../views/CalendarList.vue'
import CalendarView from '../views/CalendarView.vue'
import SignUpView from '../views/SignUpView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'calendar-list',
            component: CalendarList,
        },
        {
            path: '/calendar/:id',
            name: 'calendar',
            component: CalendarView,
        },
        {
            path: '/signup',
            name: 'signup',
            component: SignUpView,
        },
    ],
})

export default router
