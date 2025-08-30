import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { api } from '../api/client'

export default function Transactions() {
  const { accountId } = useParams()
  const [tx, setTx] = useState([])
  const [info, setInfo] = useState({})
  const [error, setError] = useState('')

  useEffect(() => {
    async function load() {
      try {
        const list = await api.get(`/api/accounts/${accountId}/transactions`)
        setTx(list.data)
        const accounts = await api.get('/api/accounts/me')
        const found = accounts.data.find(a => String(a.id) === String(accountId))
        setInfo(found || {})
      } catch (e) {
        setError('Failed to load')
      }
    }
    load()
  }, [accountId])

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h4>Transactions</h4>
        <Link to="/" className="btn btn-outline-secondary">Back</Link>
      </div>
      {info?.accountNumber && (
        <div className="alert alert-info">
          <div className="d-flex justify-content-between">
            <div>
              <div className="fw-bold">{info.accountNumber}</div>
              <div className="small text-muted">{info.type}</div>
            </div>
            <div className="fs-5">₹{info.balance}</div>
          </div>
        </div>
      )}
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="card shadow-sm">
        <div className="card-body">
          <div className="table-responsive">
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Type</th>
                  <th>Amount</th>
                  <th>Description</th>
                  <th>Time</th>
                </tr>
              </thead>
              <tbody>
                {tx.map((t, i) => (
                  <tr key={t.id}>
                    <td>{i + 1}</td>
                    <td>{t.type}</td>
                    <td>₹{t.amount}</td>
                    <td>{t.description}</td>
                    <td>{new Date(t.timestamp).toLocaleString()}</td>
                  </tr>
                ))}
                {!tx.length && (
                  <tr><td colSpan="5" className="text-center text-muted">No transactions yet</td></tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  )
}
