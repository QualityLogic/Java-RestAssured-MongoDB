const mongoose = require('mongoose')

const userSchema = new mongoose.Schema({
    name: { type: String },
    token: { type: String }
})

module.exports = mongoose.model("user", userSchema)
