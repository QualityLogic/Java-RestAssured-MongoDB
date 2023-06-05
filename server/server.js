const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('db.json')
const middlewares = jsonServer.defaults()
require('dotenv').config()
require('./config/database').connect()
const express = require('express')
const User = require('./model/user')
const jwt = require('jsonwebtoken')

const PORT = process.env.PORT

let user

server.use(middlewares)
server.use(express.json())

// Auth
server.get("/auth", async (req, res) => {
    try {
        user = await User.findOne({name: "qli"})
        console.log

        const token = jwt.sign(
            { user_id: user._id },
            process.env.TOKEN,
            {
                expiresIn: "5h"
            }
        )

        user.token = token
        console.log(user.token)

        res.status(201).json(user)
    } catch(e) {
        console.log(e)
    }
})

server.use(async (req, res, next) => {
    const { token } = req.headers

    console.log("USER: " + user)
    console.log("USER: " + user.token)
    console.log("HEADER: " + token)
    if (user.token != token) {
        res.status(401).json({message: "Authentication failed"})
        return
    }

    next()
})

server.use(router)
server.listen(PORT, () => {
    console.log(`Server is running on port: ${PORT}`)
})

module.exports = server
