import { Shield, ArrowLeft } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import Link from "next/link"

export default function EulaPage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900">
      {/* Header */}
      <header className="border-b border-white/10 bg-black/20 backdrop-blur-sm">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <div className="w-8 h-8 rounded-lg overflow-hidden">
                <img src="/auerora.png" alt="Aurora" className="w-full h-full object-cover" />
              </div>
              <h1 className="text-2xl font-bold text-white">Aurora</h1>
            </div>
            <Button variant="ghost" className="text-slate-400 hover:text-white" asChild>
              <Link href="/">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Back to Home
              </Link>
            </Button>
          </div>
        </div>
      </header>

      {/* EULA Content */}
      <section className="py-16 px-4">
        <div className="container mx-auto max-w-4xl">
          <div className="text-center mb-12">
            <h1 className="text-4xl md:text-5xl font-bold text-white mb-6 bg-gradient-to-r from-purple-400 via-pink-400 to-purple-400 bg-clip-text text-transparent">
              End User License Agreement
            </h1>
            <p className="text-slate-300 text-lg">
              Aurora is licensed under the GNU Lesser General Public License v3.0
            </p>
          </div>

          <Card className="bg-black/40 border-white/10 backdrop-blur-sm">
            <CardHeader>
              <CardTitle className="text-white flex items-center gap-2">
                <Shield className="w-6 h-6 text-green-400" />
                GNU LESSER GENERAL PUBLIC LICENSE v3.0
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="bg-green-500/10 border border-green-500/20 rounded-lg p-6">
                <h3 className="text-green-200 font-bold text-lg mb-3">� License Summary</h3>
                <p className="text-green-200">
                  Aurora is free software: you can redistribute it and/or modify it under the terms of the 
                  GNU Lesser General Public License as published by the Free Software Foundation.
                </p>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">Your Rights</h3>
                <ul className="text-slate-300 space-y-3 list-disc list-inside pl-4">
                  <li><strong>Use:</strong> You may use Aurora for any purpose, including commercial use</li>
                  <li><strong>Study:</strong> You may study how Aurora works and adapt it to your needs</li>
                  <li><strong>Share:</strong> You may redistribute copies of Aurora</li>
                  <li><strong>Modify:</strong> You may modify Aurora and distribute your modifications</li>
                </ul>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">Your Responsibilities</h3>
                <ul className="text-slate-300 space-y-3 list-disc list-inside pl-4">
                  <li>If you distribute Aurora, you must provide the source code or make it available</li>
                  <li>If you modify Aurora and distribute it, your modifications must also be under LGPL v3</li>
                  <li>You must include the original copyright notice and license text</li>
                  <li>Applications using Aurora as a library are not required to be under LGPL</li>
                </ul>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">Disclaimer</h3>
                <div className="bg-yellow-500/10 border border-yellow-500/20 rounded-lg p-4">
                  <p className="text-yellow-200">
                    Aurora is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
                    without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
                    See the GNU Lesser General Public License for more details.
                  </p>
                </div>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">Beta Software Notice</h3>
                <div className="bg-blue-500/10 border border-blue-500/20 rounded-lg p-4">
                  <p className="text-blue-200">
                    Aurora is currently in early beta. Features may be incomplete or subject to change. 
                    Please backup your Minecraft worlds before use and report issues on our GitHub repository.
                  </p>
                </div>
              </div>

              <div className="bg-slate-700/30 rounded-lg p-4 text-sm font-mono">
                <p className="text-slate-300 mb-2">
                  <strong>Copyright (C) 2025 Aurora Contributors</strong>
                </p>
                <p className="text-slate-400">
                  This program is free software: you can redistribute it and/or modify it under the terms of the 
                  GNU Lesser General Public License as published by the Free Software Foundation, either version 3 
                  of the License, or (at your option) any later version.
                </p>
              </div>

              <div className="text-center">
                <Button variant="outline" className="border-white/20 text-white hover:bg-white/10" asChild>
                  <a 
                    href="https://www.gnu.org/licenses/lgpl-3.0.html" 
                    target="_blank" 
                    rel="noopener noreferrer"
                  >
                    Read Full LGPL v3.0 License
                  </a>
                </Button>
              </div>

              <div className="pt-6 border-t border-white/10">
                <p className="text-slate-400 text-sm">
                  Last updated: June 19, 2025
                </p>
              </div>
            </CardContent>
          </Card>

          <div className="text-center mt-8">
            <Button
              size="lg"
              className="bg-gradient-to-r from-purple-600 to-pink-600 hover:from-purple-700 hover:to-pink-700 text-white"
              asChild
            >
              <Link href="/">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Return to Home
              </Link>
            </Button>
          </div>
        </div>
      </section>
    </div>
  )
}
