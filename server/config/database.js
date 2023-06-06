const mongoose = require('mongoose')
const { MONGO_URI } = process.env

exports.connect = () => {
    mongoose.connect(MONGO_URI)
    .then(() => {
        console.log('Successfully connected to the database')
    })
    .catch(e => {
        console.log('Database connection failed. Exiting...')
        console.error(e)
        process.exit(1)
    })
}
