const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('db.json')
const middlewares = jsonServer.defaults()
require('dotenv').config()
require('./config/database').connect()
const express = require('express')

const PORT = process.env.PORT

server.use(middlewares)
server.use(express.json())

// Auth
server.use((req, res, next) => {

})

server.use(router)
server.listen(PORT, () => {
    console.log(`Server is running on port: ${PORT}`)
})

module.exports = server
